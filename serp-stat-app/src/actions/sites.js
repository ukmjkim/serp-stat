import axios from 'axios';
import { ROOT_URL } from './_const.js'

//Site list
export const FETCH_SITES = 'FETCH_SITES';
export const FETCH_SITES_FULFILLED = 'FETCH_SITES_FULFILLED';
export const FETCH_SITES_REJECTED = 'FETCH_SITES_REJECTED';
export const RESET_SITES = 'RESET_SITES';

//Fetch site
export const FETCH_SITE = 'FETCH_SITE';
export const FETCH_SITE_FULFILLED = 'FETCH_SITE_FULFILLED';
export const FETCH_SITE_REJECTED = 'FETCH_SITE_REJECTED';
export const RESET_ACTIVE_SITE = 'RESET_ACTIVE_SITE';

//Create new site
export const CREATE_SITE = 'CREATE_SITE';
export const CREATE_SITE_FULFILLED = 'CREATE_SITE_FULFILLED';
export const CREATE_SITE_REJECTED = 'CREATE_SITE_REJECTED';
export const RESET_NEW_SITE = 'RESET_NEW_SITE';

//Update site
export const UPDATE_SITE = 'UPDATE_SITE';
export const UPDATE_SITE_FULFILLED = 'UPDATE_SITE_FULFILLED';
export const UPDATE_SITE_REJECTED = 'UPDATE_SITE_REJECTED';
export const RESET_UPDATED_SITE = 'RESET_UPDATED_SITE';

//Validate site fields like Title, Categries on the server
export const VALIDATE_SITE_FIELDS = 'VALIDATE_SITE_FIELDS';
export const VALIDATE_SITE_FIELDS_FULFILLED = 'VALIDATE_SITE_FIELDS_FULFILLED';
export const VALIDATE_SITE_FIELDS_REJECTED = 'VALIDATE_SITE_FIELDS_REJECTED';
export const RESET_SITE_FIELDS = 'RESET_SITE_FIELDS';

//Delete site
export const DELETE_SITE = 'DELETE_SITE';
export const DELETE_SITE_FULFILLED = 'DELETE_SITE_FULFILLED';
export const DELETE_SITE_REJECTED = 'DELETE_SITE_REJECTED';
export const RESET_DELETED_SITE = 'RESET_DELETED_SITE';

// ===========================================================================
// Fetch Sites
// ===========================================================================
export function fetchSites(userId, site) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/user/${userId}/`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(fetchSitesSuccess(response.data.sites));
        dispatch(resetNewSite());
        dispatch(resetUpdatedSite());
        dispatch(resetDeletedSite());
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
// Fetch Site
// ===========================================================================
export function fetchSite(siteId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/site/${siteId}/`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
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
    type: RESET_ACTIVE_SITE
  }
}

// ===========================================================================
// Create Site
// ===========================================================================
export function createSite(payload) {
  return (dispatch) => {
    const config = {
      method: 'post',
      data: payload,
      url: `${ROOT_URL}/site/`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(createSiteSuccess(response.data));
      })
      .catch((error) => {
        dispatch(createSiteFailure(error));
      })
  }
}

export function createSiteSuccess(payload) {
  return {
    type: CREATE_SITE_FULFILLED,
    payload
  }
}

export function createSiteFailure(error) {
  return {
    type: CREATE_SITE_REJECTED,
    payload: error
  }
}

export function resetNewSite() {
  return {
    type: RESET_NEW_SITE
  }
}

// ===========================================================================
// Update Site
// ===========================================================================
export function updateSite(siteId, payload, tokenFromStorage) {
  return (dispatch) => {
    const config = {
      method: 'put',
      data: payload,
      url: `${ROOT_URL}/user/1/site/${siteId}/`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': `Bearer ${tokenFromStorage}`
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(updateSiteSuccess(response.data));
      })
      .catch((error) => {
        dispatch(updateSiteFailure(error));
      })
  }
}

export function updateSiteSuccess(payload) {
  return {
    type: UPDATE_SITE_FULFILLED,
    payload
  }
}

export function updateSiteFailure(error) {
  return {
    type: UPDATE_SITE_REJECTED,
    payload: error
  }
}

export function resetUpdatedSite() {
  return {
    type: RESET_UPDATED_SITE
  }
}
// ===========================================================================
// Delete Site
// ===========================================================================
export function deleteSite(siteId, payload) {
  return (dispatch) => {
    const config = {
      method: 'delete',
      data: payload,
      url: `${ROOT_URL}/site/${siteId}/`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(deleteSiteSuccess(response.data));
      })
      .catch((error) => {
        dispatch(deleteSiteFailure(error));
      })
  }
}

export function deleteSiteSuccess(payload) {
  return {
    type: DELETE_SITE_FULFILLED,
    payload
  }
}

export function deleteSiteFailure(error) {
  return {
    type: DELETE_SITE_REJECTED,
    payload: error
  }
}

export function resetDeletedSite() {
  return {
    type: RESET_DELETED_SITE
  }
}
