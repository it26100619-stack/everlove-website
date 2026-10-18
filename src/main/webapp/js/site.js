(function () {
  var nav = document.querySelector('.snav');
  function onScroll() { if (nav) nav.classList.toggle('solid', window.scrollY > 40); }
  window.addEventListener('scroll', onScroll); onScroll();
  var items = document.querySelectorAll('.rv');
  if ('IntersectionObserver' in window) {
    var io = new IntersectionObserver(function (entries) {
      entries.forEach(function (e) { if (e.isIntersecting) { e.target.classList.add('in'); io.unobserve(e.target); } });
    }, { threshold: 0.15 });
    items.forEach(function (el) { io.observe(el); });
  } else { items.forEach(function (el) { el.classList.add('in'); }); }
  document.querySelectorAll('[data-count]').forEach(function (el) {
    var n = parseInt(el.getAttribute('data-count'), 10) || 0, i = 0;
    var t = setInterval(function () { i += Math.max(1, Math.ceil(n / 40)); if (i >= n) { i = n; clearInterval(t); } el.textContent = i; }, 30);
  });
})();
