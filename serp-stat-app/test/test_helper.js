import jsdom from 'jsdom';
import chai from 'chai';

const { JSDOM} = jsdom;

const { document } = (new JSDOM('')).window;
const win = document.defaultView;

global.document = document;
global.window = win;

Object.keys(window).forEach((key) => {
  if(!(key in global)) {
    global[key] = window[key];
  }
})

