import axios from "axios";
import { ROOT_URL } from '../constants/commons'
import * as types from '../constants/tags'

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
    type: types.FETCH_TAGS_FULFILLED,
    payload
  }
}

export function fetchTagsFailure(error) {
  return {
    type: types.FETCH_TAGS_REJECTED,
    payload: error
  }
}

export function resetTags() {
  return {
    type: types.RESET_TAGS
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
    type: types.FETCH_TAG_FULFILLED,
    payload
  }
}

export function fetchTagFailure(error) {
  return {
    type: types.FETCH_TAG_REJECTED,
    payload: error
  }
}

export function resetTag() {
  return {
    type: types.RESET_ACTIVE_TAG
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
    type: types.CREATE_TAG_FULFILLED,
    payload
  }
}

export function createTagFailure(error) {
  return {
    type: types.CREATE_TAG_REJECTED,
    payload: error
  }
}

export function resetNewTag() {
  return {
    type: types.RESET_NEW_TAG
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
    type: types.UPDATE_TAG_FULFILLED,
    payload
  }
}

export function updateTagFailure(error) {
  return {
    type: types.UPDATE_TAG_REJECTED,
    payload: error
  }
}

export function resetUpdatedTag() {
  return {
    type: types.RESET_UPDATED_TAG
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
    type: types.DELETE_TAG_FULFILLED,
    payload
  }
}

export function deleteTagFailure(error) {
  return {
    type: types.DELETE_TAG_REJECTED,
    payload: error
  }
}

export function resetTagTag() {
  return {
    type: types.RESET_DELETED_TAG
  }
}
