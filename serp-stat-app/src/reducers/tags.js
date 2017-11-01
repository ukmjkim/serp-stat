import * as types from '../constants/tags'

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
	case types.FETCH_TAGS:
		// start fetching tags and set loading = true
  	return { ...state, tagsList: {tags:null, error: null, loading: true} };
  case types.FETCH_TAGS_FULFILLED:
		// return list of tags and make loading = false
    return { ...state, tagsList: {tags: action.payload, error:null, loading: false} };
  case types.FETCH_TAGS_REJECTED:
		// return error and make loading = false
		// 2nd one is network or server down errors
    error = action.payload || {message: action.payload.message};
    return { ...state, tagsList: {tags: null, error: error, loading: false} };
  case types.RESET_TAGS:
		// reset tagsList to initial state
    return { ...state, tagsList: {tags: null, error:null, loading: false} };

  case types.FETCH_TAG:
    return { ...state, activeTag:{...state.activeTag, loading: true}};
  case types.FETCH_TAG_FULFILLED:
    return { ...state, activeTag: {tag: action.payload, error:null, loading: false}};
  case types.FETCH_TAG_REJECTED:
    error = action.payload || {message: action.payload.message};
    return { ...state, activeTag: {tag: null, error:error, loading:false}};
  case types.RESET_ACTIVE_TAG:
    return { ...state, activeTag: {tag: null, error:null, loading: false}};

  case types.CREATE_TAG:
  	return {...state, newTag: {...state.newTag, loading: true}}
  case types.CREATE_TAG_FULFILLED:
  	return {...state, newTag: {tag:action.payload, error:null, loading: false}}
  case types.CREATE_TAG_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, newTag: {tag:null, error:error, loading: false}}
  case types.RESET_NEW_TAG:
  	return {...state,  newTag:{tag:null, error:null, loading: false}}


  case types.DELETE_TAG:
   	return {...state, deletedTag: {...state.deletedTag, loading: true}}
  case types.DELETE_TAG_FULFILLED:
  	return {...state, deletedTag: {tag:action.payload, error:null, loading: false}}
  case types.DELETE_TAG_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, deletedTag: {tag:null, error:error, loading: false}}
  case types.RESET_DELETED_TAG:
  	return {...state,  deletedTag:{tag:null, error:null, loading: false}}

  case types.VALIDATE_TAG_FIELDS:
    return {...state, newTag:{...state.newTag, error: null, loading: true}}
  case types.VALIDATE_TAG_FIELDS_FULFILLED:
    return {...state, newTag:{...state.newTag, error: null, loading: false}}
  case types.VALIDATE_TAG_FIELDS_REJECTED:
    let result = action.payload;
    if(!result) {
      error = {message: action.payload.message};
    } else {
      error = {title: result.title, categories: result.categories, description: result.description};
    }
    return {...state, newTag:{...state.newTag, error: error, loading: false}}
  case types.RESET_TAG_FIELDS:
    return {...state, newTag:{...state.newTag, error: null, loading: null}}
  default:
    return state;
  }
}
