import React from 'react';
import ReactDOM from 'react-dom';
import {HashRouter, Route, Switch} from 'react-router-dom';
import { Provider } from "react-redux"

// Styles
// Import Font Awesome Icons Set
import 'font-awesome/css/font-awesome.min.css';
// Import Simple Line Icons Set
import 'simple-line-icons/css/simple-line-icons.css';
// Import Main styles for this application
import '../scss/style.scss'
// Temp fix for reactstrap
import '../scss/core/_dropdown-menu-right.scss'

// Containers
import Full from './containers/Full'

// Views
import Login from './views/Pages/Login'
import Register from './views/Pages/Register'
import Page404 from './views/Pages/Page404'
import Page500 from './views/Pages/Page500'

// Store
import store from "./store"

const app = (
  <Provider store={store}>
    <HashRouter>
      <Switch>
        <Route exact path="/login" name="Login Page" component={Login}/>
        <Route exact path="/register" name="Register Page" component={Register}/>
        <Route exact path="/404" name="Page 404" component={Page404}/>
        <Route exact path="/500" name="Page 500" component={Page500}/>
        <Route path="/" name="Home" component={Full}/>
      </Switch>
    </HashRouter>
  </Provider>
)
ReactDOM.render(app, document.getElementById('root'));



/*
import { applyMiddleware, createStore } from "redux";
import axios from "axios";
import { createLogger } from "redux-logger";
import thunk from "redux-thunk";
import promise from "redux-promise";

const initialState = {
  fetching: false,
  fetched: false,
  users: [],
  error: null,
}

const reducer = (state=initialState, action) => {
  switch (action.type) {
    case "FETCH_USERS_PENDING": {
      return {...state, fetching: true}
      break;
    }
    case "FETCH_USERS_REJECTED": {
      return {...state, fetching: false, error: action.payload}
      break;
    }
    case "FETCH_USERS_FULLFILLED": {
      return {...state,
        fetching: false,
        fetched: true,
        users: action.payload}
      break;
    }
  }
  return state;
}

const logger = createLogger({
  //predicate: () => process.env.NODE_ENV === 'development',
});
const middleware = applyMiddleware(promise, thunk, logger);
const store = createStore(reducer, middleware);

store.dispatch({
  type: "FETCH_USERS",
  payload: axios.get("http://rest.learncode.academy/api/wstern/users")
})
*/
