import axios from "axios";

export function tagsIsLoading(bool) {
  return {
    type: "TAGS_FETCH",
    isLoading: bool
  }
}
export function tagsHasErrored(bool, err) {
  return {
    type: "TAGS_FETCH_REJECTED",
    hasErrored: bool,
    error: err
  }
}
export function tagsHasFetched(bool) {
  return {
    type: "TAGS_FETCH_FULFILLED",
    hasFetched: bool
  }
}
export function tagsDataSuccess(payload) {
  return {
    type: "TAGS_FETCH_RESULT",
    payload
  }
}
export function tagsFetchData(siteId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: "http://localhost:8080/serp-stat-restapi/site/" + siteId + "/tags/",
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    dispatch(tagsIsLoading(true));
    axios.request(config)
      .then((response) => {
        dispatch(tagsHasFetched(true));
        dispatch(tagsDataSuccess(response.data));
      })
      .catch((err) => {
        dispatch(tagsHasErrored(true, err));
      })
  }
}
