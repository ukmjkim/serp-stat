import axios from "axios";

export function fetchSites() {
  return function(dispatch) {
    const config = {
      method: 'get',
      url: "http://localhost:8080/serp-stat-restapi/user/1/",
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    dispatch({type: "FETCH_SITES"});
    axios.request(config)
      .then((response) => {
        dispatch({type: "FETCH_SITES_FULFILLED", payload: response.data})
      })
      .catch((err) => {
        dispatch({type: "FETCH_SITES_REJECTED", payload: err})
      })
  }
}
