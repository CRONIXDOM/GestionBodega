"use strict";

(function () {
  var sidebarStorageKey = "adminHMD.sidebarMini";
  var themeStorageKey = "adminHMD.colorTheme";
  var desktopMedia = "(min-width: 992px)";

  function onReady(callback) {
    if (document.readyState === "loading") {
      document.addEventListener("DOMContentLoaded", callback);
      return;
    }

    callback();
  }

  // Algunos navegadores (Safari sobre todo) guardan la pagina viva en el
  // "back-forward cache" y al pulsar atras la restauran desde memoria sin hacer
  // ninguna peticion, asi que las cabeceras no-store del servidor no alcanzan.
  // event.persisted avisa justo de ese caso: se fuerza una recarga real para que
  // el interceptor de sesion vuelva a decidir si el usuario sigue logueado.
  window.addEventListener("pageshow", function (event) {
    if (event.persisted) {
      window.location.reload();
    }
  });

  function isDesktop() {
    return window.matchMedia(desktopMedia).matches;
  }

  function canUseStorage() {
    try {
      var testKey = sidebarStorageKey + ".test";
      window.localStorage.setItem(testKey, "1");
      window.localStorage.removeItem(testKey);
      return true;
    } catch (error) {
      return false;
    }
  }

  function getSavedMiniState(storageAvailable) {
    if (!storageAvailable) {
      return false;
    }

    return window.localStorage.getItem(sidebarStorageKey) === "true";
  }

  function saveMiniState(storageAvailable, isMini) {
    if (storageAvailable) {
      window.localStorage.setItem(sidebarStorageKey, String(isMini));
    }
  }

  function getPreferredTheme(storageAvailable) {
    var savedTheme = storageAvailable ? window.localStorage.getItem(themeStorageKey) : "";

    if (savedTheme === "dark" || savedTheme === "light") {
      return savedTheme;
    }

    return "dark";
  }

  onReady(function () {
    var body = document.body;
    var sidebarToggle = document.querySelector("[data-sidebar-toggle]");
    var themeToggles = document.querySelectorAll("[data-theme-toggle]");
    var themeIcons = document.querySelectorAll("[data-theme-icon]");
    var closeButtons = document.querySelectorAll("[data-sidebar-close]");
    var sidebarLinks = document.querySelectorAll(".sidebar-nav .nav-link");
    var mediaQuery = window.matchMedia(desktopMedia);
    var storageAvailable = canUseStorage();

    function initValidation() {
      var forms = document.querySelectorAll(".needs-validation");

      Array.prototype.forEach.call(forms, function (form) {
        form.addEventListener("submit", function (event) {
          if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
          }

          form.classList.add("was-validated");
        });
      });
    }

    // Busqueda + paginacion 100% en el cliente: la tabla ya viene completa del
    // servidor (esta app no es una SPA y no hay endpoints paginados todavia),
    // asi que esto solo oculta filas segun el texto buscado y la pagina actual.
    // El input de busqueda usa data-table-search="idDeLaTabla"; la tabla usa
    // data-page-size="N" (opcional) para activar la paginacion.
    function initTableSearch() {
      var tables = document.querySelectorAll("table[id]");

      Array.prototype.forEach.call(tables, function (table) {
        var searchInput = document.querySelector('[data-table-search="' + table.id + '"]');
        var pageSize = parseInt(table.getAttribute("data-page-size"), 10) || 0;
        var tbody = table.querySelector("tbody");
        if (!tbody || (!searchInput && !pageSize)) {
          return;
        }

        var allRows = Array.prototype.slice.call(tbody.rows);
        var dataRows = allRows.filter(function (row) {
          return !row.hasAttribute("data-empty-row");
        });
        var emptyRow = allRows.filter(function (row) {
          return row.hasAttribute("data-empty-row");
        })[0];

        if (dataRows.length === 0) {
          return;
        }

        var pagerNav = null;
        if (pageSize > 0 && dataRows.length > pageSize) {
          pagerNav = document.createElement("nav");
          pagerNav.className = "d-flex justify-content-between align-items-center flex-wrap gap-2 px-3 py-3 border-top";
          var wrapper = table.closest(".table-responsive") || table;
          wrapper.insertAdjacentElement("afterend", pagerNav);
        }

        var currentPage = 1;

        function render() {
          var query = searchInput ? searchInput.value.trim().toLowerCase() : "";
          var matched = dataRows.filter(function (row) {
            return query === "" || row.textContent.toLowerCase().indexOf(query) !== -1;
          });

          if (emptyRow) {
            emptyRow.hidden = matched.length !== 0;
          }

          var effectivePageSize = pageSize > 0 ? pageSize : matched.length || 1;
          var totalPages = Math.max(1, Math.ceil(matched.length / effectivePageSize));
          if (currentPage > totalPages) {
            currentPage = totalPages;
          }

          var start = (currentPage - 1) * effectivePageSize;
          var end = start + effectivePageSize;

          dataRows.forEach(function (row) {
            row.hidden = true;
          });
          matched.slice(start, end).forEach(function (row) {
            row.hidden = false;
          });

          if (pagerNav) {
            renderPager(totalPages, matched.length);
          }
        }

        function renderPager(totalPages, totalMatches) {
          pagerNav.innerHTML = "";

          if (totalPages <= 1) {
            return;
          }

          var info = document.createElement("span");
          info.className = "text-muted small";
          info.textContent = "Página " + currentPage + " de " + totalPages + " (" + totalMatches + " resultados)";
          pagerNav.appendChild(info);

          var ul = document.createElement("ul");
          ul.className = "pagination pagination-sm mb-0";

          function addItem(label, page, disabled, active) {
            var li = document.createElement("li");
            li.className = "page-item" + (disabled ? " disabled" : "") + (active ? " active" : "");
            var a = document.createElement("a");
            a.className = "page-link";
            a.href = "#";
            a.textContent = label;
            if (!disabled && !active) {
              a.addEventListener("click", function (event) {
                event.preventDefault();
                currentPage = page;
                render();
              });
            }
            li.appendChild(a);
            ul.appendChild(li);
          }

          addItem("Anterior", currentPage - 1, currentPage === 1, false);
          for (var p = 1; p <= totalPages; p++) {
            addItem(String(p), p, false, p === currentPage);
          }
          addItem("Siguiente", currentPage + 1, currentPage === totalPages, false);

          pagerNav.appendChild(ul);
        }

        if (searchInput) {
          searchInput.addEventListener("input", function () {
            currentPage = 1;
            render();
          });
        }

        render();
      });
    }

    function updateThemeControls(theme) {
      var nextTheme = theme === "dark" ? "light" : "dark";
      var label = "Switch to " + nextTheme + " mode";
      var iconClass = theme === "dark" ? "bi bi-sun" : "bi bi-moon-stars";

      Array.prototype.forEach.call(themeToggles, function (button) {
        button.setAttribute("aria-label", label);
        button.setAttribute("title", label);
      });

      Array.prototype.forEach.call(themeIcons, function (icon) {
        icon.className = iconClass;
      });
    }

    function applyTheme(theme) {
      document.documentElement.setAttribute("data-theme", theme);
      document.documentElement.setAttribute("data-bs-theme", theme);

      if (storageAvailable) {
        window.localStorage.setItem(themeStorageKey, theme);
      }

      updateThemeControls(theme);
    }

    function initThemeToggle() {
      applyTheme(getPreferredTheme(storageAvailable));

      Array.prototype.forEach.call(themeToggles, function (button) {
        button.addEventListener("click", function () {
          var currentTheme = document.documentElement.getAttribute("data-theme") === "dark" ? "dark" : "light";
          applyTheme(currentTheme === "dark" ? "light" : "dark");
        });
      });
    }

    initValidation();
    initTableSearch();
    initThemeToggle();

    // Initialize user profile values in UI. Provide a window.adminHMDUser object to override defaults.
    function initUserProfile() {
      var user = window.adminHMDUser || { name: "Admin Hasan", workspace: "Active Workspace", avatar: "../assets/images/avatar/avatar.jpg" };

      var sidebarNameEl = document.querySelector(".sidebar-user strong");
      var sidebarWorkspaceEl = document.querySelector(".sidebar-user small");
      var sidebarAvatar = document.querySelector(".sidebar-user .avatar-img");
      var profileNameEls = document.querySelectorAll(".profile-name");
      var profileAvatarEls = document.querySelectorAll(".profile-button .avatar-img, .profile-button img");

      if (sidebarNameEl) sidebarNameEl.textContent = user.name;
      if (sidebarWorkspaceEl) sidebarWorkspaceEl.textContent = user.workspace;
      if (sidebarAvatar && user.avatar) { sidebarAvatar.src = user.avatar; sidebarAvatar.alt = user.name; }

      Array.prototype.forEach.call(profileNameEls, function (el) { el.textContent = user.name; });
      Array.prototype.forEach.call(profileAvatarEls, function (img) { if (user.avatar) img.src = user.avatar; if (user.name) img.alt = user.name; });
    }

    initUserProfile();

    // El sidebar tiene su propio scroll (position: fixed + overflow-y: auto) y cada
    // click en un link recarga la pagina completa (no es una SPA), asi que sin esto
    // el scroll siempre vuelve arriba y hay que bajar de nuevo para llegar a una
    // opcion cercana a la que se acaba de usar.
    function initSidebarScroll() {
      var sidebar = document.querySelector(".admin-sidebar");
      if (!sidebar) {
        return;
      }
      var key = "adminHMD.sidebarScrollTop";
      try {
        var saved = window.sessionStorage.getItem(key);
        if (saved !== null) {
          sidebar.scrollTop = parseInt(saved, 10) || 0;
        }
        sidebar.addEventListener("scroll", function () {
          window.sessionStorage.setItem(key, String(sidebar.scrollTop));
        }, { passive: true });
      } catch (error) {
        // almacenamiento no disponible (p.ej. navegacion privada): se ignora y
        // el scroll simplemente se comporta como antes.
      }
    }

    initSidebarScroll();

    if (!sidebarToggle) {
      return;
    }

    function setClass(element, className, enabled) {
      if (enabled) {
        element.classList.add(className);
      } else {
        element.classList.remove(className);
      }
    }

    function setToggleExpanded() {
      var expanded = isDesktop()
        ? !body.classList.contains("sidebar-mini")
        : body.classList.contains("sidebar-open");

      sidebarToggle.setAttribute("aria-expanded", String(expanded));
    }

    function closeMobileSidebar() {
      body.classList.remove("sidebar-open");
      setToggleExpanded();
    }

    function toggleSidebar() {
      if (isDesktop()) {
        body.classList.toggle("sidebar-mini");
        saveMiniState(storageAvailable, body.classList.contains("sidebar-mini"));
      } else {
        body.classList.toggle("sidebar-open");
      }

      setToggleExpanded();
    }

    function addCloseHandlers(items) {
      Array.prototype.forEach.call(items, function (item) {
        item.addEventListener("click", function () {
          if (!isDesktop()) {
            closeMobileSidebar();
          }
        });
      });
    }

    if (getSavedMiniState(storageAvailable) && isDesktop()) {
      body.classList.add("sidebar-mini");
    }

    sidebarToggle.addEventListener("click", toggleSidebar);
    addCloseHandlers(closeButtons);
    addCloseHandlers(sidebarLinks);
    setToggleExpanded();

    function handleBreakpointChange() {
      if (isDesktop()) {
        body.classList.remove("sidebar-open");
        setClass(body, "sidebar-mini", getSavedMiniState(storageAvailable));
      } else {
        body.classList.remove("sidebar-mini");
      }

      setToggleExpanded();
    }

    if (mediaQuery.addEventListener) {
      mediaQuery.addEventListener("change", handleBreakpointChange);
    } else if (mediaQuery.addListener) {
      mediaQuery.addListener(handleBreakpointChange);
    }
  });
})();
