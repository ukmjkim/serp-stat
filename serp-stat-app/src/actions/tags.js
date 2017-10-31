import axios from "axios";
import { ROOT_URL } from './../commons/const.js'

//Tag list
export const FETCH_TAGS = 'FETCH_TAGS';
export const FETCH_TAGS_FULFILLED = 'FETCH_TAGS_FULFILLED';
export const FETCH_TAGS_REJECTED = 'FETCH_TAGS_REJECTED';
export const RESET_TAGS = 'RESET_TAGS';

//Fetch tag
export const FETCH_TAG = 'FETCH_TAG';
export const FETCH_TAG_FULFILLED = 'FETCH_TAG_FULFILLED';
export const FETCH_TAG_REJECTED = 'FETCH_TAG_REJECTED';
export const RESET_ACTIVE_TAG = 'RESET_ACTIVE_TAG';

//Create new tag
export const CREATE_TAGS = 'CREATE_TAG';
export const CREATE_TAGS_FULFILLED = 'CREATE_TAG_FULFILLED';
export const CREATE_TAGS_REJECTED = 'CREATE_TAG_REJECTED';
export const RESET_NEW_TAG = 'RESET_NEW_TAG';

//Validate tag fields like Title, Categries on the server
export const VALIDATE_TAG_FIELDS = 'VALIDATE_TAG_FIELDS';
export const VALIDATE_TAG_FIELDS_FULFILLED = 'VALIDATE_TAG_FIELDS_FULFILLED';
export const VALIDATE_TAG_FIELDS_REJECTED = 'VALIDATE_TAG_FIELDS_REJECTED';
export const RESET_TAG_FIELDS = 'RESET_TAG_FIELDS';

//Delete tag
export const DELETE_TAG = 'DELETE_TAG';
export const DELETE_TAG_FULFILLED = 'DELETE_TAG_FULFILLED';
export const DELETE_TAG_REJECTED = 'DELETE_TAG_REJECTED';
export const RESET_DELETED_TAG = 'RESET_DELETED_TAG';

// ===========================================================================
// Tags
// ===========================================================================
export function fetchTags(siteId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/site/${siteId}/tags`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(fetchTagsSuccess(response.data.tags));
      })
      .catch((error) => {
        dispatch(fetchTagsFailure(error));
      })
  }
}

export function fetchTagsSuccess(payload) {
  return {
    type: FETCH_TAGS_FULFILLED,
    payload
  }
}

export function fetchTagsFailure(error) {
  return {
    type: FETCH_TAGS_REJECTED,
    payload: error
  }
}

export function resetTags() {
  return {
    type: RESET_TAGS
  }
}

// ===========================================================================
// Tag
// ===========================================================================
export function fetchTag(siteId, tagId) {
  return (dispatch) => {
    const config = {
      method: 'get',
      url: `${ROOT_URL}/site/${siteId}/tag/${tagId}`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8"
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(fetchTagSuccess(response.data));
      })
      .catch((error) => {
        dispatch(fetchTagFailure(error));
      })
  }
}

export function fetchTagSuccess(payload) {
  return {
    type: FETCH_TAG_FULFILLED,
    payload
  }
}

export function fetchTagFailure(error) {
  return {
    type: FETCH_TAG_REJECTED,
    payload: error
  }
}

export function resetTag() {
  return {
    type: RESET_ACTIVE_TAG
  }
}

// ===========================================================================
// Create Tag
// ===========================================================================
export function createTag(siteId, payload) {
  return (dispatch) => {
    const config = {
      method: 'post',
      data: payload,
      url: `${ROOT_URL}/site/${siteId}/tag`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(createTagSuccess(response.data));
      })
      .catch((error) => {
        dispatch(createTagFailure(error));
      })
  }
}

export function createTagSuccess(payload) {
  return {
    type: CREATE_TAG_FULFILLED,
    payload
  }
}

export function createTagFailure(error) {
  return {
    type: CREATE_TAG_REJECTED,
    payload: error
  }
}

export function resetNewTag() {
  return {
    type: RESET_NEW_TAG
  }
}

// ===========================================================================
// Update Tag
// ===========================================================================
export function updateTag(siteId, payload, tokenFromStorage) {
  return (dispatch) => {
    const config = {
      method: 'put',
      data: payload,
      url: `${ROOT_URL}/site/${siteId}/tag/${payload.id}`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': `Bearer ${tokenFromStorage}`
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(updateTagSuccess(response.data));
      })
      .catch((error) => {
        dispatch(updateTagFailure(error));
      })
  }
}

export function updateTagSuccess(payload) {
  return {
    type: UPDATE_TAG_FULFILLED,
    payload
  }
}

export function updateTagFailure(error) {
  return {
    type: UPDATE_TAG_REJECTED,
    payload: error
  }
}

export function resetUpdatedTag() {
  return {
    type: RESET_UPDATED_TAG
  }
}
// ===========================================================================
// Delete Tag
// ===========================================================================
export function deleteTag(siteId, payload) {
  return (dispatch) => {
    const config = {
      method: 'delete',
      data: payload,
      url: `${ROOT_URL}/site/${siteId}/tag/${payload.id}`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
    };

    axios.request(config)
      .then((response) => {
        dispatch(deleteTagSuccess(response.data));
      })
      .catch((error) => {
        dispatch(deleteTagFailure(error));
      })
  }
}

export function deleteTagSuccess(payload) {
  return {
    type: DELETE_TAG_FULFILLED,
    payload
  }
}

export function deleteTagFailure(error) {
  return {
    type: DELETE_TAG_REJECTED,
    payload: error
  }
}

export function resetTagTag() {
  return {
    type: RESET_DELETED_TAG
  }
}
