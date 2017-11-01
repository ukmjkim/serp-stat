import * as types from '../constants/sites'

const INITIAL_STATE =
			{
				sitesList: {sites: null, error:null, loading: false},
				newSite:{site:null, error: null, loading: false},
				activeSite:{site:null, error:null, loading: false},
				updatedSite: {site: null, error:null, loading: false},
				deletedSite: {site: null, error:null, loading: false},
			};

export default function reducer(state=INITIAL_STATE, action) {
	let error;
  switch(action.type) {
	case types.FETCH_SITES:
		// start fetching sites and set loading = true
  	return { ...state, sitesList: {sites:null, error: null, loading: true} };
  case types.FETCH_SITES_FULFILLED:
		// return list of sites and make loading = false
    return { ...state, sitesList: {sites: action.payload, error:null, loading: false} };
  case types.FETCH_SITES_REJECTED:
		// return error and make loading = false
		// 2nd one is network or server down errors
    error = action.payload || {message: action.payload.message};
    return { ...state, sitesList: {error: error, loading: false} };
  case types.RESET_SITES:
		// reset sitesList to initial state
    return { ...state, sitesList: {sites:null, error:null, loading: false} };

  case types.FETCH_SITE:
    return { ...state, activeSite:{...state.activeSite, loading: true}};
  case types.FETCH_SITE_FULFILLED:
    return { ...state, activeSite: {site: action.payload, error:null, loading: false}};
  case types.FETCH_SITE_REJECTED:
    error = action.payload || {message: action.payload.message};
    return { ...state, activeSite: {site: null, error:error, loading:false}};
  case types.RESET_ACTIVE_SITE:
    return { ...state, activeSite: {site: null, error:null, loading: false}};

  case types.CREATE_SITE:
  	return {...state, newSite: {...state.newSite, loading: true}}
  case types.CREATE_SITE_FULFILLED:
  	return {...state, newSite: {site:action.payload, error:null, loading: false}}
  case types.CREATE_SITE_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, newSite: {site:null, error:error, loading: false}}
  case types.RESET_NEW_SITE:
  	return {...state,  newSite:{site:null, error:null, loading: false}}

  case types.UPDATE_SITE:
  	return {...state, updatedSite: {...state.updatedSite, loading: true}}
  case types.UPDATE_SITE_FULFILLED:
  	return {...state, updatedSite: {site:action.payload, error:null, loading: false}}
  case types.UPDATE_SITE_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, updatedSite: {site:null, error:error, loading: false}}
	case types.RESET_UPDATED_SITE:
  	return {...state,  updatedSite:{site:null, error:null, loading: false}}

  case types.DELETE_SITE:
   	return {...state, deletedSite: {...state.deletedSite, loading: true}}
  case types.DELETE_SITE_FULFILLED:
  	return {...state, deletedSite: {site:action.payload, error:null, loading: false}}
  case types.DELETE_SITE_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, deletedSite: {site:null, error:error, loading: false}}
  case types.RESET_DELETED_SITE:
  	return {...state,  deletedSite:{site:null, error:null, loading: false}}

  case types.VALIDATE_SITE_FIELDS:
    return {...state, newSite:{...state.newSite, error: null, loading: true}}
  case types.VALIDATE_SITE_FIELDS_FULFILLED:
    return {...state, newSite:{...state.newSite, error: null, loading: false}}
  case types.VALIDATE_SITE_FIELDS_REJECTED:
    let result = action.payload;
    if(!result) {
      error = {message: action.payload.message};
    } else {
      error = {title: result.title, categories: result.categories, description: result.description};
    }
    return {...state, newSite:{...state.newSite, error: error, loading: false}}
  case types.RESET_SITE_FIELDS:
    return {...state, newSite:{...state.newSite, error: null, loading: null}}
  default:
    return state;
  }
}
