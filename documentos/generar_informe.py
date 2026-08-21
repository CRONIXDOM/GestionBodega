# -*- coding: utf-8 -*-
"""Reconstruye el cuerpo del informe: mejor estructura + dashboard, manteniendo el diseno."""
import re, html

BASE = 'unpacked/word/document.xml'
ORANGE = 'FF9900'
BLACK  = '1A1A1A'
GREY_L = 'F2F2F2'
GREY_M = 'D9D9D9'
GREY_T = '595959'
CONTENT_W = 8838

FONTS = ('<w:rFonts w:ascii="Times New Roman" w:eastAsia="Times New Roman" '
         'w:hAnsi="Times New Roman" w:cs="Times New Roman"/>')
LANG = '<w:lang w:val="es-EC" w:eastAsia="es-EC"/><w14:ligatures w14:val="none"/>'

def esc(t):
    return html.escape(t, quote=False)

def rpr(sz=24, b=False, color=None, caps=False, i=False):
    out = FONTS
    if b: out += '<w:b/><w:bCs/>'
    if i: out += '<w:i/><w:iCs/>'
    if caps: out += '<w:caps/>'
    if color: out += '<w:color w:val="%s"/>' % color
    out += '<w:kern w:val="0"/><w:sz w:val="%d"/><w:szCs w:val="%d"/>' % (sz, sz)
    out += LANG
    return out

def run(text, **fmt):
    sp = ' xml:space="preserve"' if text != text.strip() else ''
    return '<w:r><w:rPr>%s</w:rPr><w:t%s>%s</w:t></w:r>' % (rpr(**fmt), sp, esc(text))

def para(runs, jc=None, sz=24, b=False, color=None, spacing='auto', ind=None,
         pstyle=None, outline=None, numid=None, keepnext=False, bottom_border=None,
         shd=None, contextual=False, after=None, before=None):
    """runs: str (xml ya armado) o lista de tuplas (texto, fmtdict)."""
    if isinstance(runs, list):
        runs = ''.join(run(t, **f) for t, f in runs)
    ppr = ''
    if pstyle: ppr += '<w:pStyle w:val="%s"/>' % pstyle
    if keepnext: ppr += '<w:keepNext/><w:keepLines/>'
    if numid: ppr += '<w:numPr><w:ilvl w:val="0"/><w:numId w:val="%d"/></w:numPr>' % numid
    if bottom_border:
        ppr += ('<w:pBdr><w:bottom w:val="single" w:sz="%d" w:space="2" w:color="%s"/></w:pBdr>'
                % (bottom_border[1], bottom_border[0]))
    if shd: ppr += '<w:shd w:val="clear" w:color="auto" w:fill="%s"/>' % shd
    if spacing == 'auto':
        ppr += ('<w:spacing w:before="100" w:beforeAutospacing="1" w:after="100" '
                'w:afterAutospacing="1" w:line="240" w:lineRule="auto"/>')
    else:
        ppr += ('<w:spacing w:before="%d" w:after="%d" w:line="240" w:lineRule="auto"/>'
                % (before or 0, after if after is not None else 0))
    if ind is not None: ppr += '<w:ind w:left="%d"/>' % ind
    if contextual: ppr += '<w:contextualSpacing/>'
    if jc: ppr += '<w:jc w:val="%s"/>' % jc
    if outline is not None: ppr += '<w:outlineLvl w:val="%d"/>' % outline
    ppr += '<w:rPr>%s</w:rPr>' % rpr(sz=sz, b=b, color=color)
    return '<w:p><w:pPr>%s</w:pPr>%s</w:p>' % (ppr, runs)

# ---------------------------------------------------------------- encabezados
def h_section(num, title, sep=True):
    """Titulo de seccion con el formato original: Times New Roman bold 18pt."""
    p = para([('%s %s' % (num, title), dict(sz=36, b=True))],
             sz=36, b=True, outline=1, keepnext=True)
    return (hr() + p) if sep else p

def h_sub(num, title):
    return para([('%s %s' % (num, title), dict(sz=28, b=True))],
                sz=28, b=True, outline=2, keepnext=True)

def body(text, jc='both'):
    return para([(text, dict(sz=24))], jc=jc, sz=24)

def body_rich(chunks, jc='both'):
    return para([(t, f) for t, f in chunks], jc=jc, sz=24)

def bullet(text, numid=14, bold_head=None, keep=False):
    chunks = []
    if bold_head:
        chunks.append((bold_head, dict(sz=24, b=True)))
    chunks.append((text, dict(sz=24)))
    return para(chunks, sz=24, numid=numid, pstyle='Prrafodelista', jc='both', keepnext=keep)

def caption(text):
    return para([(text, dict(sz=20, i=True, color=GREY_T))], jc='center', sz=20,
                spacing='fixed', before=60, after=180)

def note(text):
    return para([('Nota: ', dict(sz=20, b=True, color=GREY_T)),
                 (text, dict(sz=20, i=True, color=GREY_T))],
                jc='both', sz=20, spacing='fixed', before=60, after=180)

_hr_id = [1030]

def hr():
    """Filete horizontal gris: mismo recurso de diseno usado en el documento original."""
    _hr_id[0] += 1
    r = rpr()
    return ('<w:p><w:pPr><w:spacing w:after="0" w:line="240" w:lineRule="auto"/>'
            '<w:rPr>%s</w:rPr></w:pPr><w:r><w:rPr>%s</w:rPr><w:pict>'
            '<v:rect id="_x0000_i%d" style="width:0;height:1.5pt" o:hralign="center" '
            'o:hrstd="t" o:hr="t" fillcolor="#a0a0a0" stroked="f"/></w:pict></w:r></w:p>'
            % (r, r, _hr_id[0]))

def spacer(sz=12):
    return para('', sz=sz, spacing='fixed', after=0)

# ------------------------------------------------------------------- celdas
KEEP = '<w:keepNext/><w:keepLines/>'

NOBORD = ('<w:tcBorders><w:top w:val="nil"/><w:left w:val="nil"/>'
          '<w:bottom w:val="nil"/><w:right w:val="nil"/></w:tcBorders>')

def tc(width, content, fill=None, borders=None, valign='center', margins=None,
       span=None, vmerge=None):
    pr = '<w:tcW w:w="%d" w:type="dxa"/>' % width
    if span: pr += '<w:gridSpan w:val="%d"/>' % span
    if borders is not None: pr += borders
    if fill: pr += '<w:shd w:val="clear" w:color="auto" w:fill="%s"/>' % fill
    if margins:
        t, r, b, l = margins
        pr += ('<w:tcMar><w:top w:w="%d" w:type="dxa"/><w:left w:w="%d" w:type="dxa"/>'
               '<w:bottom w:w="%d" w:type="dxa"/><w:right w:w="%d" w:type="dxa"/></w:tcMar>'
               % (t, l, b, r))
    if valign: pr += '<w:vAlign w:val="%s"/>' % valign
    return '<w:tc><w:tcPr>%s</w:tcPr>%s</w:tc>' % (pr, content)

def tr(cells, height=None, header=False, cantsplit=True):
    pr = ''
    if cantsplit: pr += '<w:cantSplit/>'
    if height: pr += '<w:trHeight w:val="%d" w:hRule="atLeast"/>' % height
    if header: pr += '<w:tblHeader/>'
    trpr = '<w:trPr>%s</w:trPr>' % pr if pr else ''
    return '<w:tr>%s%s</w:tr>' % (trpr, ''.join(cells))

def tbl(grid, rows, style=None, borders=None, cellmar=None, width=None, jc=None):
    pr = ''
    if style: pr += '<w:tblStyle w:val="%s"/>' % style
    pr += ('<w:tblW w:w="%d" w:type="dxa"/>' % (width if width is not None else sum(grid)))
    if jc: pr += '<w:jc w:val="%s"/>' % jc
    if borders is not None: pr += borders
    if cellmar is not None:
        t, r, b, l = cellmar
        pr += ('<w:tblCellMar><w:top w:w="%d" w:type="dxa"/><w:left w:w="%d" w:type="dxa"/>'
               '<w:bottom w:w="%d" w:type="dxa"/><w:right w:w="%d" w:type="dxa"/></w:tblCellMar>'
               % (t, l, b, r))
    pr += ('<w:tblLook w:val="04A0" w:firstRow="1" w:lastRow="0" w:firstColumn="1" '
           'w:lastColumn="0" w:noHBand="0" w:noVBand="1"/>')
    g = ''.join('<w:gridCol w:w="%d"/>' % w for w in grid)
    return ('<w:tbl><w:tblPr>%s</w:tblPr><w:tblGrid>%s</w:tblGrid>%s</w:tbl>'
            % (pr, g, ''.join(rows)))

BORD_NONE = ('<w:tblBorders><w:top w:val="nil"/><w:left w:val="nil"/><w:bottom w:val="nil"/>'
             '<w:right w:val="nil"/><w:insideH w:val="nil"/><w:insideV w:val="nil"/></w:tblBorders>')
BORD_ROWS = ('<w:tblBorders><w:top w:val="nil"/><w:left w:val="nil"/>'
             '<w:bottom w:val="single" w:sz="4" w:space="0" w:color="%s"/>'
             '<w:right w:val="nil"/>'
             '<w:insideH w:val="single" w:sz="4" w:space="0" w:color="%s"/>'
             '<w:insideV w:val="nil"/></w:tblBorders>' % (GREY_M, GREY_M))

def cellp(text, sz=22, b=False, color=None, jc='left', caps=False, after=40, before=40,
          keep=True):
    return para([(text, dict(sz=sz, b=b, color=color, caps=caps))], jc=jc, sz=sz,
                spacing='fixed', before=before, after=after, keepnext=keep)

# ------------------------------------------------------------ barra (grafico)
def bar(pct, width=3300, fill=ORANGE, track=GREY_L):
    """Barra horizontal como tabla anidada de 2 celdas proporcionales."""
    filled = int(round(width * pct / 100.0))
    if pct > 0:
        filled = max(filled, 25)
    filled = min(filled, width)
    rest = width - filled
    empty_p = para('', sz=2, spacing='fixed', after=0)
    cells, grid = [], []
    if filled > 0:
        grid.append(filled)
        cells.append(tc(filled, empty_p, fill=fill, borders=NOBORD, valign='center'))
    if rest > 0:
        grid.append(rest)
        cells.append(tc(rest, empty_p, fill=track, borders=NOBORD, valign='center'))
    inner = tbl(grid, [tr(cells, height=150)], borders=BORD_NONE, cellmar=(0, 0, 0, 0))
    inner = inner.replace('<w:trHeight w:val="150" w:hRule="atLeast"/>',
                          '<w:trHeight w:val="150" w:hRule="exact"/>')
    return inner + para('', sz=2, spacing='fixed', after=0)

# ------------------------------------------------------------------ tarjetas
def kpi_card(width, value, label, dark=True):
    fill = BLACK if dark else GREY_L
    vcol = ORANGE if dark else BLACK
    lcol = 'FFFFFF' if dark else GREY_T
    p1 = para([(value, dict(sz=44, b=True, color=vcol))], jc='center', sz=44,
              spacing='fixed', before=60, after=0)
    p2 = para([(label, dict(sz=16, b=True, color=lcol, caps=True))], jc='center', sz=16,
              spacing='fixed', before=0, after=60)
    return tc(width, p1 + p2, fill=fill, borders=NOBORD, valign='center',
              margins=(60, 60, 60, 60))

def kpi_row(cards, dark=True):
    """4 tarjetas separadas por columnas-espaciador."""
    cw, gap = 2058, 202
    grid, cells = [], []
    for i, (val, lab) in enumerate(cards):
        if i:
            grid.append(gap)
            cells.append(tc(gap, para('', sz=2, spacing='fixed', after=0),
                            borders=NOBORD, valign='center'))
        grid.append(cw)
        cells.append(kpi_card(cw, val, lab, dark=dark))
    return tbl(grid, [tr(cells, height=620)], borders=BORD_NONE, cellmar=(0, 0, 0, 0))

# ------------------------------------------------- tabla de datos + barra
def data_table(headers, rows, grid, total=None, aligns=None):
    """headers: list[str]; rows: list[list]; celda barra = ('BAR', pct)."""
    if aligns is None:
        aligns = ['left'] + ['center'] * (len(headers) - 1)
    hcells = []
    for i, h in enumerate(headers):
        jc = aligns[i]
        hcells.append(tc(grid[i], cellp(h, sz=20, b=True, color='FFFFFF', jc=jc, caps=True,
                                        before=60, after=60),
                         fill=BLACK, valign='center'))
    out = [tr(hcells, header=True)]
    for r in rows:
        cells = []
        for i, v in enumerate(r):
            if isinstance(v, tuple) and v[0] == 'BAR':
                cells.append(tc(grid[i], bar(v[1], width=grid[i] - 260),
                                valign='center', margins=(60, 130, 60, 130)))
            else:
                cells.append(tc(grid[i], cellp(v, jc=aligns[i]), valign='center'))
        out.append(tr(cells, height=280))
    if total:
        cells = []
        for i, v in enumerate(total):
            if isinstance(v, tuple) and v[0] == 'BAR':
                cells.append(tc(grid[i], bar(v[1], width=grid[i] - 260), fill=GREY_L,
                                valign='center', margins=(60, 130, 60, 130)))
            else:
                cells.append(tc(grid[i], cellp(v, b=True, jc=aligns[i]), fill=GREY_L,
                                valign='center'))
        out.append(tr(cells, height=280))
    out[-1] = out[-1].replace(KEEP, '')
    return tbl(grid, out, borders=BORD_ROWS, cellmar=(0, 108, 0, 108))

# ------------------------------------------------ tablas con estilo original
def styled_table(style, headers, rows, grid, total=None):
    """Conserva los estilos de tabla originales (Grid Table 4 - Enfasis 2 / 4)."""
    CNF_HDR = ('<w:cnfStyle w:val="100000000000" w:firstRow="1" w:lastRow="0" w:firstColumn="0" '
               'w:lastColumn="0" w:oddVBand="0" w:evenVBand="0" w:oddHBand="0" w:evenHBand="0" '
               'w:firstRowFirstColumn="0" w:firstRowLastColumn="0" w:lastRowFirstColumn="0" '
               'w:lastRowLastColumn="0"/>')
    CNF_COL = ('<w:cnfStyle w:val="001000000000" w:firstRow="0" w:lastRow="0" w:firstColumn="1" '
               'w:lastColumn="0" w:oddVBand="0" w:evenVBand="0" w:oddHBand="0" w:evenHBand="0" '
               'w:firstRowFirstColumn="0" w:firstRowLastColumn="0" w:lastRowFirstColumn="0" '
               'w:lastRowLastColumn="0"/>')

    def cell(w, text, first, jc, b=False):
        pr = (CNF_COL if first else '') + '<w:tcW w:w="%d" w:type="dxa"/>' % w
        pr += '<w:vAlign w:val="center"/>'
        p = para([(text, dict(sz=24, b=b))], jc=jc, sz=24, spacing='fixed',
                 before=40, after=40, keepnext=True)
        return '<w:tc><w:tcPr>%s</w:tcPr>%s</w:tc>' % (pr, p)

    rws = []
    hc = [cell(grid[i], h, i == 0, 'center') for i, h in enumerate(headers)]
    rws.append('<w:tr><w:trPr>%s<w:cantSplit/><w:tblHeader/></w:trPr>%s</w:tr>'
               % (CNF_HDR, ''.join(hc)))
    for r in rows:
        cs = [cell(grid[i], v, i == 0, 'left' if i == 0 else 'center')
              for i, v in enumerate(r)]
        rws.append('<w:tr><w:trPr><w:cantSplit/></w:trPr>%s</w:tr>' % ''.join(cs))
    if total:
        cs = [cell(grid[i], v, i == 0, 'left' if i == 0 else 'center', b=True)
              for i, v in enumerate(total)]
        rws.append('<w:tr><w:trPr><w:cantSplit/></w:trPr>%s</w:tr>' % ''.join(cs))
    rws[-1] = rws[-1].replace(KEEP, '')
    return tbl(grid, rws, style=style)

# =============================================================== CONTENIDO ===
src = open(BASE, encoding='utf-8').read()
head = src[:src.index('<w:body>') + len('<w:body>')]
tail = src[src.rindex('</w:body>'):]
bodyxml = src[src.index('<w:body>') + len('<w:body>'):src.rindex('</w:body>')]
parts = re.findall(r'<w:p\b(?:[^>]*/>|.*?</w:p>)|<w:tbl>.*?</w:tbl>|<w:sectPr\b.*?</w:sectPr>',
                   bodyxml, re.S)
P_TITULO1, P_TITULO2 = parts[1], parts[2]
P_FOTO = parts[35]
SECTPR = parts[51]

# --- datos de la semana (tal como los reporta el informe original) ---
RECIBIDAS, DECLARADAS, LIBERADAS, DESPACHADAS, REZAGADAS = 2445, 1305, 1651, 582, 48
AF_AUTO, AF_FIS, AF_DOC = 1295, 10, 0
TRANSP = [('Tramaco', 286), ('MV', 110), ('Exacto Carga – Cuenca', 61),
          ('Exacto Carga – Manta', 45), ('Servientrega', 30), ('Urbano', 1)]
SUMA_TRANSP = sum(v for _, v in TRANSP)          # 533
TOTAL_REPORTADO = 582

def pc(n, d):
    return '%.1f%%' % (100.0 * n / d) if d else '0.0%'

def pv(n, d):
    return 100.0 * n / d if d else 0.0

doc = []
doc.append(spacer())
doc.append(P_TITULO1)
doc.append(P_TITULO2)

# ---- barra de identificacion del informe -------------------------------
gw = [2946, 2946, 2946]
def infolabel(w, t):
    return tc(w, cellp(t, sz=18, b=True, color='FFFFFF', jc='center', caps=True,
                       before=50, after=50), fill=BLACK, borders=NOBORD, valign='center')
def infoval(w, t):
    return tc(w, cellp(t, sz=22, jc='center', before=60, after=60),
              fill=GREY_L, borders=NOBORD, valign='center')

info_rows = [
    tr([infolabel(gw[0], 'Área'), infolabel(gw[1], 'Cargo'), infolabel(gw[2], 'Elaborado por')]),
    tr([infoval(gw[0], 'Aduana y Operaciones'), infoval(gw[1], 'Coordinadora Jr.'),
        infoval(gw[2], 'Cindy Sánchez')]),
    tr([infolabel(gw[0], 'Semana'), infolabel(gw[1], 'Período'), infolabel(gw[2], 'Tipo de informe')]),
    tr([infoval(gw[0], 'Agosto – Semana 3'), infoval(gw[1], '17-08-2026 al 21-08-2026'),
        infoval(gw[2], 'Ejecutivo semanal')]),
]
doc.append(tbl(gw, info_rows, borders=BORD_NONE, cellmar=(0, 80, 0, 80)))
doc.append(spacer())

# ---- 1. Resumen ejecutivo ----------------------------------------------
doc.append(h_section('1.', 'Resumen Ejecutivo', sep=False))
doc.append(body_rich([
    ('Durante la semana comprendida entre el ', dict(sz=24)),
    ('17-08-2026', dict(sz=24, b=True)),
    (' y el ', dict(sz=24)),
    ('21-08-2026', dict(sz=24, b=True)),
    (' se gestionó la recepción, revisión documental, nacionalización y despacho de carga '
     'Courier, manteniendo coordinación con las áreas de bodega, transporte y servicio al cliente.',
     dict(sz=24)),
]))
doc.append(body_rich([
    ('En total se procesaron ', dict(sz=24)),
    ('2445 guías', dict(sz=24, b=True)),
    (', de las cuales ', dict(sz=24)),
    ('1305', dict(sz=24, b=True)),
    (' fueron declaradas, ', dict(sz=24)),
    ('1651', dict(sz=24, b=True)),
    (' liberadas y ', dict(sz=24)),
    ('582', dict(sz=24, b=True)),
    (' despachadas dentro de la planificación operativa establecida. Se registraron ', dict(sz=24)),
    ('48 guías rezagadas', dict(sz=24, b=True)),
    (' y ', dict(sz=24)),
    ('6 guías pendientes por declarar', dict(sz=24, b=True)),
    (', cuyo detalle y seguimiento se presentan en las secciones siguientes.', dict(sz=24)),
]))

# ---- 2. Dashboard -------------------------------------------------------
doc.append(h_section('2.', 'Tablero de Indicadores (Dashboard)'))
doc.append(body('El siguiente tablero resume el desempeño operativo de la semana y permite '
                'identificar, de un vistazo, el avance del flujo de carga y los focos que '
                'requieren atención.'))

doc.append(h_sub('2.1', 'Indicadores clave de la semana'))
doc.append(kpi_row([('2445', 'Guías procesadas'), ('1305', 'Guías declaradas'),
                    ('1651', 'Guías liberadas'), ('582', 'Guías despachadas')], dark=True))
doc.append(spacer(10))
doc.append(kpi_row([('48', 'Guías rezagadas'), ('6', 'Pendientes por declarar'),
                    ('10', 'Aforos físicos'), ('8', 'Guías con novedad')], dark=False))
doc.append(spacer(10))

doc.append(h_sub('2.2', 'Flujo operativo de la carga'))
grid4 = [2700, 1350, 1000, 3788]
doc.append(data_table(
    ['Etapa del proceso', 'Guías', '%', 'Avance'],
    [['Guías recibidas', str(RECIBIDAS), pc(RECIBIDAS, RECIBIDAS),
      ('BAR', pv(RECIBIDAS, RECIBIDAS))],
     ['Guías declaradas', str(DECLARADAS), pc(DECLARADAS, RECIBIDAS),
      ('BAR', pv(DECLARADAS, RECIBIDAS))],
     ['Guías liberadas', str(LIBERADAS), pc(LIBERADAS, RECIBIDAS),
      ('BAR', pv(LIBERADAS, RECIBIDAS))],
     ['Guías despachadas', str(DESPACHADAS), pc(DESPACHADAS, RECIBIDAS),
      ('BAR', pv(DESPACHADAS, RECIBIDAS))],
     ['Guías rezagadas', str(REZAGADAS), pc(REZAGADAS, RECIBIDAS),
      ('BAR', pv(REZAGADAS, RECIBIDAS))]],
    grid4))
doc.append(caption('Porcentajes calculados sobre el total de 2445 guías procesadas en la semana.'))

doc.append(h_sub('2.3', 'Distribución de aforos'))
doc.append(data_table(
    ['Tipo de control', 'Cantidad', '%', 'Participación'],
    [['Aforos automáticos', str(AF_AUTO), pc(AF_AUTO, DECLARADAS), ('BAR', pv(AF_AUTO, DECLARADAS))],
     ['Aforos físicos', str(AF_FIS), pc(AF_FIS, DECLARADAS), ('BAR', pv(AF_FIS, DECLARADAS))],
     ['Aforos documentales', str(AF_DOC), pc(AF_DOC, DECLARADAS), ('BAR', pv(AF_DOC, DECLARADAS))]],
    grid4,
    total=['Total de guías declaradas', str(DECLARADAS), '100.0%', ('BAR', 100.0)]))

doc.append(caption('Porcentajes calculados sobre las 1305 guías declaradas en la semana.'))

doc.append(h_sub('2.4', 'Despachos por transportadora'))
doc.append(data_table(
    ['Transportadora', 'Guías', '%', 'Participación'],
    [[n, str(v), pc(v, SUMA_TRANSP), ('BAR', pv(v, SUMA_TRANSP))] for n, v in TRANSP],
    grid4,
    total=['Total según detalle', str(SUMA_TRANSP), '100.0%', ('BAR', 100.0)]))
doc.append(note('los porcentajes se calculan sobre el detalle de %d guías. El total de despachos '
                'reportado en la semana es de %d guías, por lo que existe una diferencia de %d '
                'guías pendiente de verificación.'
                % (SUMA_TRANSP, TOTAL_REPORTADO, TOTAL_REPORTADO - SUMA_TRANSP)))

doc.append(h_sub('2.5', 'Semáforo de novedades y alertas'))
grid_sem = [2500, 1400, 1500, 3438]
def estado_cell(w, txt, alerta):
    fill = ORANGE if alerta else GREY_L
    return tc(w, cellp(txt, sz=18, b=True, color=BLACK, jc='center', caps=True,
                       before=60, after=60), fill=fill, valign='center')

sem_rows = [tr([tc(grid_sem[0], cellp('Foco de control', sz=20, b=True, color='FFFFFF',
                                      caps=True, before=60, after=60), fill=BLACK, valign='center'),
                tc(grid_sem[1], cellp('Cantidad', sz=20, b=True, color='FFFFFF', jc='center',
                                      caps=True, before=60, after=60), fill=BLACK, valign='center'),
                tc(grid_sem[2], cellp('Estado', sz=20, b=True, color='FFFFFF', jc='center',
                                      caps=True, before=60, after=60), fill=BLACK, valign='center'),
                tc(grid_sem[3], cellp('Detalle', sz=20, b=True, color='FFFFFF', caps=True,
                                      before=60, after=60), fill=BLACK, valign='center')],
               header=True)]
SEM = [('Pendientes por declarar', '6 guías', 'Atención', True,
        'Núcleo inválido, falta de cédula del migrante, categoría A y reingreso al núcleo familiar'),
       ('Guías con sobrepeso', '3 guías', 'Atención', True,
        'Dos guías autorizadas para entrega y una en KN'),
       ('Guías con intervención', '5 guías', 'Atención', True,
        'Entrega express; una guía por cantidades comerciales'),
       ('Aforos físicos', '10 guías', 'En curso', False,
        'Retrasos por categoría G desde el lunes 17'),
       ('Archivo documental', '—', 'En curso', False,
        'Avance en el archivo de mayo 19/25 MASTER'),
       ('Carga arribada en la semana', '—', 'En curso', False,
        'En proceso de declaración')]
for foco, cant, est, alerta, det in SEM:
    sem_rows.append(tr([tc(grid_sem[0], cellp(foco, b=True), valign='center'),
                        tc(grid_sem[1], cellp(cant, jc='center'), valign='center'),
                        estado_cell(grid_sem[2], est, alerta),
                        tc(grid_sem[3], cellp(det), valign='center')], height=280))
sem_rows[-1] = sem_rows[-1].replace(KEEP, '')
doc.append(tbl(grid_sem, sem_rows, borders=BORD_ROWS, cellmar=(0, 108, 0, 108)))

# ---- 3. Gestion aduanera ------------------------------------------------
doc.append(h_section('3.', 'Gestión Aduanera'))
doc.append(body('Durante la semana se registraron los siguientes controles aduaneros:'))
doc.append(styled_table('Tablaconcuadrcula4-nfasis2',
                        ['Tipo de Control', 'Cantidad', '% del total'],
                        [['Aforos automáticos', str(AF_AUTO), pc(AF_AUTO, DECLARADAS)],
                         ['Aforos físicos', str(AF_FIS), pc(AF_FIS, DECLARADAS)],
                         ['Aforos documentales', str(AF_DOC), pc(AF_DOC, DECLARADAS)]],
                        [3838, 2500, 2500],
                        total=['Total de guías procesadas', str(DECLARADAS), '100.0%']))
doc.append(body('Los procesos de control se gestionaron conforme a los procedimientos '
                'establecidos hasta la liberación y autorización de salida de la carga.'))

# ---- 4. Despachos -------------------------------------------------------
doc.append(h_section('4.', 'Despachos por Transportadora'))
doc.append(body('Los envíos liberados fueron distribuidos mediante las siguientes transportadoras:'))
doc.append(styled_table('Tablaconcuadrcula4-nfasis4',
                        ['Transportadora', 'Guías despachadas', '% del detalle'],
                        [[n, str(v), pc(v, SUMA_TRANSP)] for n, v in TRANSP],
                        [3838, 2500, 2500],
                        total=['Total según detalle', str(SUMA_TRANSP), '100.0%']))
doc.append(body('Se mantuvo coordinación con las transportadoras para garantizar la correcta '
                'entrega y continuidad del servicio logístico.'))

# ---- 5. Novedades operativas -------------------------------------------
doc.append(h_section('5.', 'Novedades Operativas'))
doc.append(body('Durante la presente semana se presentaron las siguientes novedades, '
                'agrupadas por tipo de incidencia:'))

doc.append(h_sub('5.1', 'Pendientes por declarar (6 guías)'))
doc.append(data_table(
    ['N.º', 'Guía', 'Motivo'],
    [['1', 'ECA7800170285', 'Núcleo inválido'],
     ['2', 'ECA0904670439', 'Sin cédula del migrante'],
     ['3', 'ECA770003014', 'Categoría A (contiene otro contenido)'],
     ['4', 'ECA7700015035', 'Reingreso al núcleo familiar'],
     ['5', 'ECA7700015036', 'Reingreso al núcleo familiar'],
     ['6', 'ECA7700015037', 'Reingreso al núcleo familiar']],
    [700, 2600, 5538], aligns=['center', 'left', 'left']))

doc.append(h_sub('5.2', 'Guías con sobrepeso (3 guías)'))
doc.append(data_table(
    ['Guía', 'Peso', 'Acción'],
    [['ECA7700014939', '4.50 kg', 'Entregar'],
     ['ECA7700014947', '4.28 kg', 'Entregar'],
     ['ECA0404801437', '4.10 kg', 'KN']],
    [2900, 1800, 4138], aligns=['left', 'center', 'left']))

doc.append(h_sub('5.3', 'Guías con intervención (5 guías) – entrega express'))
doc.append(data_table(
    ['Guía', 'Observación'],
    [['ECA7700014952', 'Intervención – entrega express'],
     ['ECA7700014953', 'Cantidades comerciales'],
     ['ECA7700014955', 'Intervención – entrega express'],
     ['ECA7700014974', 'Intervención – entrega express'],
     ['ECA7700014975', 'Intervención – entrega express']],
    [2900, 5938], aligns=['left', 'left']))
doc.append(spacer(10))
doc.append(re.sub(r'<w:ind w:left="1440"/>', '', P_FOTO).replace(
    '<w:jc w:val="both"/>', ''))
doc.append(caption('Evidencia fotográfica de la revisión física de carga con intervención.'))

doc.append(h_sub('5.4', 'Otras novedades'))
doc.append(bullet('Archivo: se está avanzando en el archivo de mayo 19/25 MASTER.', numid=14))
doc.append(bullet('La carga arribada en la semana se encuentra en proceso de declaración.', numid=14))
doc.append(bullet('Aforos físicos: se registraron retrasos por categoría G desde el lunes 17.',
                  numid=14))
doc.append(body('Las incidencias en aduana fueron gestionadas oportunamente para evitar '
                'retrasos mayores en el despacho de la carga.'))

# ---- 6. Acciones realizadas --------------------------------------------
doc.append(h_section('6.', 'Acciones Realizadas'))
doc.append(bullet('Seguimiento a los procesos de aforo y liberación de carga en aduana.',
                  numid=14, keep=True))
doc.append(bullet('Alternancia del personal en la realización de aforos.',
                  numid=14, keep=True))
doc.append(bullet('Coordinación con bodega y transportadoras para la salida de envíos.', numid=14))

# ---- 7. Conclusion ------------------------------------------------------
doc.append(h_section('7.', 'Conclusión'))
doc.append(body('Las operaciones de la semana se desarrollaron con normalidad, manteniendo el '
                'flujo de nacionalización y despacho de la carga. Se continuará reforzando los '
                'controles operativos y documentales para optimizar los tiempos de gestión en '
                'las siguientes semanas.'))
doc.append(hr())
doc.append(spacer(16))

# ---- Firmas -------------------------------------------------------------
FIRMA_BORD = ('<w:tcBorders><w:top w:val="single" w:sz="6" w:space="0" w:color="%s"/>'
              '<w:left w:val="nil"/><w:bottom w:val="nil"/><w:right w:val="nil"/></w:tcBorders>'
              % BLACK)
fg = [4119, 600, 4119]
firma_rows = [
    tr([tc(fg[0], para('', sz=16, spacing='fixed', after=0), borders=NOBORD),
        tc(fg[1], para('', sz=16, spacing='fixed', after=0), borders=NOBORD),
        tc(fg[2], para('', sz=16, spacing='fixed', after=0), borders=NOBORD)], height=560),
    tr([tc(fg[0], cellp('Elaborado por', sz=20, b=True, jc='center', caps=True,
                        before=60, after=0) +
           cellp('Cindy Sánchez – Coordinadora Jr.', sz=20, jc='center', before=0, after=60),
           borders=FIRMA_BORD, valign='top'),
        tc(fg[1], para('', sz=16, spacing='fixed', after=0), borders=NOBORD),
        tc(fg[2], cellp('Revisado por', sz=20, b=True, jc='center', caps=True,
                        before=60, after=0) +
           cellp('Jefatura de Aduana y Operaciones', sz=20, jc='center', before=0, after=60),
           borders=FIRMA_BORD, valign='top')]),
]
doc.append(tbl(fg, firma_rows, borders=BORD_NONE, cellmar=(0, 0, 0, 0)))
doc.append(para('', sz=24, pstyle='NormalWeb'))
doc.append(SECTPR)

out = head + ''.join(doc) + tail
open(BASE, 'w', encoding='utf-8').write(out)
print('OK bytes=%d parts=%d' % (len(out), len(doc)))
