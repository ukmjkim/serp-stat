import {
	FETCH_TAGS, FETCH_TAGS_FULFILLED, FETCH_TAGS_REJECTED, RESET_TAGS,
	FETCH_TAG, FETCH_TAG_FULFILLED, FETCH_TAG_REJECTED, RESET_ACTIVE_TAG,
	CREATE_TAG, CREATE_TAG_FULFILLED,  CREATE_TAG_REJECTED, RESET_NEW_TAG,
	VALIDATE_TAG_FIELDS, VALIDATE_TAG_FIELDS_FULFILLED, VALIDATE_TAG_FIELDS_REJECTED, RESET_TAG_FIELDS,
  DELETE_TAG, DELETE_TAG_FULFILLED, DELETE_TAG_REJECTED, RESET_DELETED_TAG
} from '../actions/tags';

const INITIAL_STATE =
			{
				tagsList: {tags: null, error:null, loading: false},
				newTag:{tag:null, error: null, loading: false},
				activeTag:{tag:null, error:null, loading: false},
				deletedTag: {tag: null, error:null, loading: false},
			};

export default function reducer(state=INITIAL_STATE, action) {
	let error;
  switch(action.type) {
	case FETCH_TAGS:
		// start fetching tags and set loading = true
  	return { ...state, tagsList: {tags:null, error: null, loading: true} };
  case FETCH_TAGS_FULFILLED:
		// return list of tags and make loading = false
    return { ...state, tagsList: {tags: action.payload, error:null, loading: false} };
  case FETCH_TAGS_REJECTED:
		// return error and make loading = false
		// 2nd one is network or server down errors
    error = action.payload || {message: action.payload.message};
    return { ...state, tagsList: {tags: null, error: error, loading: false} };
  case RESET_TAGS:
		// reset tagsList to initial state
    return { ...state, tagsList: {tags: null, error:null, loading: false} };

  case FETCH_TAG:
    return { ...state, activeTag:{...state.activeTag, loading: true}};
  case FETCH_TAG_FULFILLED:
    return { ...state, activeTag: {tag: action.payload, error:null, loading: false}};
  case FETCH_TAG_REJECTED:
    error = action.payload || {message: action.payload.message};
    return { ...state, activeTag: {tag: null, error:error, loading:false}};
  case RESET_ACTIVE_TAG:
    return { ...state, activeTag: {tag: null, error:null, loading: false}};

  case CREATE_TAG:
  	return {...state, newTag: {...state.newTag, loading: true}}
  case CREATE_TAG_FULFILLED:
  	return {...state, newTag: {tag:action.payload, error:null, loading: false}}
  case CREATE_TAG_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, newTag: {tag:null, error:error, loading: false}}
  case RESET_NEW_TAG:
  	return {...state,  newTag:{tag:null, error:null, loading: false}}


  case DELETE_TAG:
   	return {...state, deletedTag: {...state.deletedTag, loading: true}}
  case DELETE_TAG_FULFILLED:
  	return {...state, deletedTag: {tag:action.payload, error:null, loading: false}}
  case DELETE_TAG_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, deletedTag: {tag:null, error:error, loading: false}}
  case RESET_DELETED_TAG:
  	return {...state,  deletedTag:{tag:null, error:null, loading: false}}

  case VALIDATE_TAG_FIELDS:
    return {...state, newTag:{...state.newTag, error: null, loading: true}}
  case VALIDATE_TAG_FIELDS_FULFILLED:
    return {...state, newTag:{...state.newTag, error: null, loading: false}}
  case VALIDATE_TAG_FIELDS_REJECTED:
    let result = action.payload;
    if(!result) {
      error = {message: action.payload.message};
    } else {
      error = {title: result.title, categories: result.categories, description: result.description};
    }
    return {...state, newTag:{...state.newTag, error: error, loading: false}}
  case RESET_TAG_FIELDS:
    return {...state, newTag:{...state.newTag, error: null, loading: null}}
  default:
    return state;
  }
}
