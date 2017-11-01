import axios from 'axios';
import { ROOT_URL } from '../constants/commons'
import * as types from '../constants/sites'

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
    type: types.FETCH_SITES_FULFILLED,
    payload
  }
}

export function fetchSitesFailure(error) {
  return {
    type: types.FETCH_SITES_REJECTED,
    payload: error
  }
}

export function resetSites() {
  return {
    type: types.RESET_SITES
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
    type: types.FETCH_SITE_FULFILLED,
    payload
  }
}

export function fetchSiteFailure(error) {
  return {
    type: types.FETCH_SITE_REJECTED,
    payload: error
  }
}

export function resetSite() {
  return {
    type: types.RESET_ACTIVE_SITE
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
    type: types.CREATE_SITE_FULFILLED,
    payload
  }
}

export function createSiteFailure(error) {
  return {
    type: types.CREATE_SITE_REJECTED,
    payload: error
  }
}

export function resetNewSite() {
  return {
    type: types.RESET_NEW_SITE
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
    type: types.UPDATE_SITE_FULFILLED,
    payload
  }
}

export function updateSiteFailure(error) {
  return {
    type: types.UPDATE_SITE_REJECTED,
    payload: error
  }
}

export function resetUpdatedSite() {
  return {
    type: types.RESET_UPDATED_SITE
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
    type: types.DELETE_SITE_FULFILLED,
    payload
  }
}

export function deleteSiteFailure(error) {
  return {
    type: types.types.DELETE_SITE_REJECTED,
    payload: error
  }
}

export function resetDeletedSite() {
  return {
    type: types.RESET_DELETED_SITE
  }
}
