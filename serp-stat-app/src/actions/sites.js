import axios from "axios";
import { ROOT_URL } from './_const.js'

//Site list
export const FETCH_SITES = 'FETCH_SITES';
export const FETCH_SITES_FULFILLED = 'FETCH_SITES_FULFILLED';
export const FETCH_SITES_REJECTED = 'FETCH_SITES_REJECTED';
export const RESET_SITES = 'RESET_SITES';

//Create new site
export const CREATE_SITES = 'CREATE_SITE';
export const CREATE_SITES_FULFILLED = 'CREATE_SITE_FULFILLED';
export const CREATE_SITES_REJECTED = 'CREATE_SITE_REJECTED';
export const RESET_NEW_SITE = 'RESET_NEW_SITE';

//Validate site fields like Title, Categries on the server
export const VALIDATE_SITE_FIELDS = 'VALIDATE_SITE_FIELDS';
export const VALIDATE_SITE_FIELDS_FULFILLED = 'VALIDATE_SITE_FIELDS_FULFILLED';
export const VALIDATE_SITE_FIELDS_REJECTED = 'VALIDATE_SITE_FIELDS_REJECTED';
export const RESET_SITE_FIELDS = 'RESET_SITE_FIELDS';

//Fetch site
export const FETCH_SITE = 'FETCH_SITE';
export const FETCH_SITE_FULFILLED = 'FETCH_SITE_FULFILLED';
export const FETCH_SITE_REJECTED = 'FETCH_SITE_REJECTED';
export const RESET_ACTIVE_SITE = 'RESET_ACTIVE_SITE';

//Delete site
export const DELETE_SITE = 'DELETE_SITE';
export const DELETE_SITE_FULFILLED = 'DELETE_SITE_FULFILLED';
export const DELETE_SITE_REJECTED = 'DELETE_SITE_REJECTED';
export const RESET_DELETED_SITE = 'RESET_DELETED_SITE';

// ===========================================================================
// Sites
// ===========================================================================
export function fetchSites(userId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/user/${userId}/`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(fetchSitesSuccess(response.data.sites));
        if (response.data.sites.length > 0) {
          dispatch(fetchSite(response.data.sites[0].id));
        }
      })
      .catch((error) => {
        dispatch(fetchSitesFailure(error));
      })
  }
}

export function fetchSitesSuccess(payload) {
  return {
    type: FETCH_SITES_FULFILLED,
    payload
  }
}

export function fetchSitesFailure(error) {
  return {
    type: FETCH_SITES_REJECTED,
    payload: error
  }
}

export function resetSites() {
  return {
    type: RESET_SITES
  }
}

// ===========================================================================
// Site
// ===========================================================================
export function fetchSite(siteId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/site/${siteId}/`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(fetchSiteSuccess(response.data));
      })
      .catch((error) => {
        dispatch(fetchSiteFailure(error));
      })
  }
}

export function fetchSiteSuccess(payload) {
  return {
    type: FETCH_SITE_FULFILLED,
    payload
  }
}

export function fetchSiteFailure(error) {
  return {
    type: FETCH_SITE_REJECTED,
    payload: error
  }
}

export function resetSite() {
  return {
    type: RESET_SITES
  }
}
