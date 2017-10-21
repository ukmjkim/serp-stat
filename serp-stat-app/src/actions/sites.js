import { tagsFetchData } from "./tags";
import axios from "axios";

export function sitesIsLoading(bool) {
  return {
    type: "SITES_FETCH",
    isLoading: bool
  }
}
export function sitesHasErrored(bool, err) {
  return {
    type: "SITES_FETCH_REJECTED",
    hasErrored: bool,
    error: err
  }
}
export function sitesDataSuccess(payload) {
  return {
    type: "SITES_FETCH_FULFILLED",
    isFetched: true,
    payload
  }
}
export function sitesFetchData(userId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: "http://localhost:8080/serp-stat-restapi/user/" + userId + "/",
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    dispatch(sitesIsLoading(true));
    axios.request(config)
      .then((response) => {
        dispatch(sitesDataSuccess(response.data));
        if (response.data.sites.length > 0) {
          dispatch(sitesSelected(response.data.sites[0]));
        }
      })
      .catch((err) => {
        dispatch(sitesHasErrored(true, err));
      })
  }
}
export function sitesSelected(site) {
  return {
    type: "SITES_SELECTED",
    selectedSite: site
  }
}
