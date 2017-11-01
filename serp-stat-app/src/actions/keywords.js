import axios from "axios";
import { ROOT_URL } from '../constants/commons'
import * as types from '../constants/keywords'

// ===========================================================================
// Keywords
// ===========================================================================
export function fetchKeywords(siteId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/site/${siteId}/keyword`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(fetchKeywordsSuccess(response.data));
      })
      .catch((error) => {
        dispatch(fetchKeywordsFailure(error));
      })
  }
}

export function fetchKeywordsSuccess(payload) {
  return {
    type: types.FETCH_KEYWORDS_FULFILLED,
    payload
  }
}

export function fetchKeywordsFailure(error) {
  return {
    type: types.FETCH_KEYWORDS_REJECTED,
    payload: error
  }
}

export function resetKeywords() {
  return {
    type: types.RESET_KEYWORDS
  }
}

// ===========================================================================
// Paginated Keywords
// ===========================================================================
export function fetchPaginatedKeywords(siteId, offset, size) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/site/${siteId}/keyword/paginated?offset=${offset}&size=${size}`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(fetchPaginatedKeywordsSuccess(response.data));
      })
      .catch((error) => {
        dispatch(fetchPaginatedKeywordsFailure(error));
      })
  }
}

export function fetchPaginatedKeywordsSuccess(payload) {
  return {
    type: types.FETCH_PAGINATED_KEYWORDS_FULFILLED,
    payload
  }
}

export function fetchPaginatedKeywordsFailure(error) {
  return {
    type: types.FETCH_PAGINATED_KEYWORDS_REJECTED,
    payload: error
  }
}

export function resetPaginatedKeywords() {
  return {
    type: types.RESET_PAGINATED_KEYWORDS
  }
}


// ===========================================================================
// Keywords Count
// ===========================================================================
export function fetchKeywordsCount(siteId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/site/${siteId}/keyword/count`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(fetchKeywordsCountSuccess(response.data));
      })
      .catch((error) => {
        dispatch(fetchKeywordsCountFailure(error));
      })
  }
}

export function fetchKeywordsCountSuccess(payload) {
  return {
    type: types.FETCH_KEYWORDS_COUNT_FULFILLED,
    payload
  }
}

export function fetchKeywordsCountFailure(error) {
  return {
    type: types.FETCH_KEYWORDS_COUNT_REJECTED,
    payload: error
  }
}

export function resetKeywordsCount() {
  return {
    type: types.RESET_KEYWORDS_COUNT
  }
}

// ===========================================================================
// Keyword
// ===========================================================================
export function fetchKeyword(siteId, keywordId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/site/${siteId}/keyword/${keywordId}`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(fetchKeywordSuccess(response.data));
      })
      .catch((error) => {
        dispatch(fetchKeywordFailure(error));
      })
  }
}

export function fetchKeywordSuccess(payload) {
  return {
    type: types.FETCH_KEYWORD_FULFILLED,
    payload
  }
}

export function fetchKeywordFailure(error) {
  return {
    type: types.FETCH_KEYWORD_REJECTED,
    payload: error
  }
}

export function resetKeyword() {
  return {
    type: types.RESET_ACTIVE_KEYWORD
  }
}


// ===========================================================================
// Create Keyword
// ===========================================================================
export function createKeyword(siteId, payload) {
  return (dispatch) => {
    const config = {
      method: 'post',
      data: payload,
      url: `${ROOT_URL}/site/${siteId}/keyword`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(createKeywordSuccess(response.data));
      })
      .catch((error) => {
        dispatch(createKeywordFailure(error));
      })
  }
}

export function createKeywordSuccess(payload) {
  return {
    type: types.CREATE_KEYWORD_FULFILLED,
    payload
  }
}

export function createKeywordFailure(error) {
  return {
    type: types.CREATE_KEYWORD_REJECTED,
    payload: error
  }
}

export function resetNewKeyword() {
  return {
    type: types.RESET_NEW_KEYWORD
  }
}

// ===========================================================================
// Update Keyword
// ===========================================================================
export function updateKeyword(siteId, payload, tokenFromStorage) {
  return (dispatch) => {
    const config = {
      method: 'put',
      data: payload,
      url: `${ROOT_URL}/site/${siteId}/keyword/${payload.id}`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': `Bearer ${tokenFromStorage}`
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(updateKeywordSuccess(response.data));
      })
      .catch((error) => {
        dispatch(updateKeywordFailure(error));
      })
  }
}

export function updateKeywordSuccess(payload) {
  return {
    type: types.UPDATE_KEYWORD_FULFILLED,
    payload
  }
}

export function updateKeywordFailure(error) {
  return {
    type: types.UPDATE_KEYWORD_REJECTED,
    payload: error
  }
}

export function resetUpdatedKeyword() {
  return {
    type: types.RESET_UPDATED_KEYWORD
  }
}
// ===========================================================================
// Delete Keyword
// ===========================================================================
export function deleteKeyword(siteId, payload) {
  return (dispatch) => {
    const config = {
      method: 'delete',
      data: payload,
      url: `${ROOT_URL}/site/${siteId}/keyword/${payload.id}`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(deleteKeywordSuccess(response.data));
      })
      .catch((error) => {
        dispatch(deleteKeywordFailure(error));
      })
  }
}

export function deleteKeywordSuccess(payload) {
  return {
    type: types.DELETE_KEYWORD_FULFILLED,
    payload
  }
}

export function deleteKeywordFailure(error) {
  return {
    type: types.DELETE_KEYWORD_REJECTED,
    payload: error
  }
}

export function resetDeletedKeyword() {
  return {
    type: types.RESET_DELETED_KEYWORD
  }
}
