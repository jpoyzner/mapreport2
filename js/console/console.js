//include this file on your page for the console to work
(function() {
    var fileref = document.createElement('script');
    fileref.setAttribute('type', 'text/javascript');
    fileref.setAttribute('src', '//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.10/require.min.js');
    fileref.setAttribute('data-main', '//50.62.80.222/MapReport/js/consolemodule.js'); //or wherever it's hosted currently
    document.getElementsByTagName('head')[0].appendChild(fileref);
})();