import axios from "axios";
import { ROOT_URL } from './_const.js'

//Tag list
export const FETCH_TAGS = 'FETCH_TAGS';
export const FETCH_TAGS_FULFILLED = 'FETCH_TAGS_FULFILLED';
export const FETCH_TAGS_REJECTED = 'FETCH_TAGS_REJECTED';
export const RESET_TAGS = 'RESET_TAGS';

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

//Fetch tag
export const FETCH_TAG = 'FETCH_TAG';
export const FETCH_TAG_FULFILLED = 'FETCH_TAG_FULFILLED';
export const FETCH_TAG_REJECTED = 'FETCH_TAG_REJECTED';
export const RESET_ACTIVE_TAG = 'RESET_ACTIVE_TAG';

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
  payload.site.link = `/site/${payload.id}`;
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
    type: RESET_TAGS
  }
}
