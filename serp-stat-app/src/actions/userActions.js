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
