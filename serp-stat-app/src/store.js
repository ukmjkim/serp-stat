import { applyMiddleware, createStore } from "redux"

import { createLogger } from 'redux-logger';
import thunk from "redux-thunk"
import promise from "redux-promise"

import reducers from "./reducers"

const logger = createLogger({
  //predicate: () => process.env.NODE_ENV === 'development',
});
const middleware = applyMiddleware(promise, thunk, logger);
const store = createStore(reducers, middleware);

export default store;


// https://medium.com/@stowball/a-dummys-guide-to-redux-and-thunk-in-react-d8904a7005d3
// http://www.thegreatcodeadventure.com/the-react-plus-redux-container-pattern/
