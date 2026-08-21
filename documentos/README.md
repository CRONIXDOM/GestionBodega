# Informe semanal Box Cargo

- `PLANTILLA_ORIGINAL_BOX_CARGO.docx`: documento original recibido.
- `INFORME_BOX_CARGO_SEMANAL_AGOSTO_S3.docx`: versión con la estructura mejorada y el
  tablero de indicadores (dashboard), conservando el diseño original (encabezado con
  banda negra y logo, pie de página, tipografía Times New Roman y estilos de tabla).
- `generar_informe.py`: script que reconstruye `word/document.xml` a partir del
  documento original descomprimido. Uso:

  ```bash
  unzip -q PLANTILLA_ORIGINAL_BOX_CARGO.docx -d unpacked/
  python3 generar_informe.py            # reescribe unpacked/word/document.xml
  (cd unpacked && zip -Xrq ../salida.docx .)
  ```

Los datos de la semana están centralizados en el bloque `CONTENIDO` del script
(`RECIBIDAS`, `DECLARADAS`, `LIBERADAS`, `DESPACHADAS`, `REZAGADAS`, `TRANSP`, …),
de modo que el informe de la siguiente semana solo requiere actualizar esas cifras.
