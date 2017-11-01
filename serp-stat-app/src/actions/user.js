import axios from "axios";
import { ROOT_URL } from '../constants/commons'
import { fetchSites } from './sites'
import * as types from '../constants/user'

// ===========================================================================
// User: https://github.com/pburtchaell/redux-promise-middleware/issues/85
// ===========================================================================
export function fetchUser(userId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/user/${userId}/`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    return axios.request(config)
      .then((response) => {
        dispatch(fetchUserSuccess(response.data));
      })
      .catch((error) => {
        dispatch(fetchUserFailure(error));
      })
  }
}

export function fetchUserSuccess(payload) {
  return {
    type: types.FETCH_USER_FULFILLED,
    payload
  }
}

export function fetchUserFailure(error) {
  return {
    type: types.FETCH_USER_REJECTED,
    payload: error
  }
}

export function resetActiveUser() {
  return {
    type: types.RESET_ACTIVE_USER
  }
}

/*
import axios from "axios";

export function fetchUser() {
  return function(dispatch) {
    const config = {
      method: 'get',
      url: "http://localhost:8080/serp-stat-restapi/user/1",
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    dispatch({type: "FETCH_USER"});
    axios.request(config)
      .then((response) => {
        console.log("fetchUser: " + response);
        dispatch({type: "FETCH_USER_FULFILLED", payload: response.data})
      })
      .catch((err) => {
        dispatch({type: "FETCH_USER_REJECTED", payload: err})
      })
  }
}
 */
/*
axios({
  method: 'post',
  url: 'https://onesignal.com/api/v1/notifications',
  headers: {
    'Authorization': 'Basic NTRjZDY1O....',
    'Content-type': 'application/json; charset=utf-8',
    'Access-Control-Allow-Origin':'*',
    'Access-Control-Allow-Methods': 'POST',
    'Access-Control-Allow-Headers':'*',
    'cache-control': 'no-cache'
  },
  data: {
    "app_id": "8e86143e-00.......",
    "contents": {
      "en": "test api"
    },
    "included_segments": [
      "All"
    ]
  }
}).catch(function (error) {
  if (error.response) {
    console.log('error-response-data-'+error.response.data);
    console.log('error-response-status-'+error.response.status);
    console.log('error-response-headers-'+error.response.headers);
  } else if (error.request) {
    console.log('error-request-'+error.request);
  } else {
    console.log('Error', error.message);
  }
});
 */
