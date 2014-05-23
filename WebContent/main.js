//var gui = require('nw.gui');
//var win = gui.Window.get();
//win.x = 0;
//win.y = 0;
//win.maximize();

//win.resizeTo(window.screen.width, window.screen.height);
//1920×1080

// Load native UI library
var gui = require('nw.gui');

// Print arguments
console.log(gui.App.argv);

console.log("on the node webket --------------! yeah");

gui.App.clearCache();

//// Quit current app
//gui.App.quit();
//
//// Get the name field in manifest
//gui.App.manifest.name