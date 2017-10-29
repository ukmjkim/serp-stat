import axios from "axios";
import { ROOT_URL } from './_const.js'

//Keyword list
export const FETCH_KEYWORDS = 'FETCH_KEYWORDS';
export const FETCH_KEYWORDS_FULFILLED = 'FETCH_KEYWORDS_FULFILLED';
export const FETCH_KEYWORDS_REJECTED = 'FETCH_KEYWORDS_REJECTED';
export const RESET_KEYWORDS = 'RESET_KEYWORD';

//Fetch keyword
export const FETCH_KEYWORD = 'FETCH_KEYWORD';
export const FETCH_KEYWORD_FULFILLED = 'FETCH_KEYWORD_FULFILLED';
export const FETCH_KEYWORD_REJECTED = 'FETCH_KEYWORD_REJECTED';
export const RESET_ACTIVE_KEYWORD = 'RESET_ACTIVE_KEYWORD';

//Create new keyword
export const CREATE_KEYWORD = 'CREATE_KEYWORD';
export const CREATE_KEYWORD_FULFILLED = 'CREATE_KEYWORD_FULFILLED';
export const CREATE_KEYWORD_REJECTED = 'CREATE_KEYWORD_REJECTED';
export const RESET_NEW_KEYWORD = 'RESET_NEW_KEYWORD';

//Update site
export const UPDATE_SITE = 'UPDATE_SITE';
export const UPDATE_SITE_FULFILLED = 'UPDATE_SITE_FULFILLED';
export const UPDATE_SITE_REJECTED = 'UPDATE_SITE_REJECTED';
export const RESET_UPDATED_SITE = 'RESET_UPDATED_SITE';

//Validate keyword fields like Title, Categries on the server
export const VALIDATE_KEYWORD_FIELDS = 'VALIDATE_KEYWORD_FIELDS';
export const VALIDATE_KEYWORD_FIELDS_FULFILLED = 'VALIDATE_KEYWORD_FIELDS_FULFILLED';
export const VALIDATE_KEYWORD_FIELDS_REJECTED = 'VALIDATE_KEYWORD_FIELDS_REJECTED';
export const RESET_KEYWORD_FIELDS = 'RESET_KEYWORD_FIELDS';

//Delete keyword
export const DELETE_KEYWORD = 'DELETE_KEYWORD';
export const DELETE_KEYWORD_FULFILLED = 'DELETE_KEYWORD_FULFILLED';
export const DELETE_KEYWORD_REJECTED = 'DELETE_KEYWORD_REJECTED';
export const RESET_DELETED_KEYWORD = 'RESET_DELETED_KEYWORD';

// ===========================================================================
// Keywords
// ===========================================================================
export function fetchKeywords(siteId) {
  console.log("============================== fetchKeywords");
  console.log(`${ROOT_URL}/site/${siteId}/keyword`);
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
    type: FETCH_KEYWORDS_FULFILLED,
    payload
  }
}

export function fetchKeywordsFailure(error) {
  return {
    type: FETCH_KEYWORDS_REJECTED,
    payload: error
  }
}

export function resetKeywords() {
  return {
    type: RESET_KEYWORDS
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
    type: FETCH_KEYWORD_FULFILLED,
    payload
  }
}

export function fetchKeywordFailure(error) {
  return {
    type: FETCH_KEYWORD_REJECTED,
    payload: error
  }
}

export function resetKeyword() {
  return {
    type: RESET_ACTIVE_KEYWORD
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
    type: CREATE_KEYWORD_FULFILLED,
    payload
  }
}

export function createKeywordFailure(error) {
  return {
    type: CREATE_KEYWORD_REJECTED,
    payload: error
  }
}

export function resetNewKeyword() {
  return {
    type: RESET_NEW_KEYWORD
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
    type: UPDATE_KEYWORD_FULFILLED,
    payload
  }
}

export function updateKeywordFailure(error) {
  return {
    type: UPDATE_KEYWORD_REJECTED,
    payload: error
  }
}

export function resetUpdatedKeyword() {
  return {
    type: RESET_UPDATED_KEYWORD
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
    type: DELETE_KEYWORD_FULFILLED,
    payload
  }
}

export function deleteKeywordFailure(error) {
  return {
    type: DELETE_KEYWORD_REJECTED,
    payload: error
  }
}

export function resetDeletedKeyword() {
  return {
    type: RESET_DELETED_KEYWORD
  }
}
