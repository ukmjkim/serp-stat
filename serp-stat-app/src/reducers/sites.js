import {
	FETCH_SITES, FETCH_SITES_FULFILLED, FETCH_SITES_REJECTED, RESET_SITES,
	FETCH_SITE, FETCH_SITE_FULFILLED, FETCH_SITE_REJECTED, RESET_ACTIVE_SITE,
	CREATE_SITE, CREATE_SITE_FULFILLED,  CREATE_SITE_REJECTED, RESET_NEW_SITE,
	VALIDATE_SITE_FIELDS, VALIDATE_SITE_FIELDS_FULFILLED, VALIDATE_SITE_FIELDS_REJECTED, RESET_SITE_FIELDS,
  DELETE_SITE, DELETE_SITE_FULFILLED, DELETE_SITE_REJECTED, RESET_DELETED_SITE
} from '../actions/sites';

const INITIAL_STATE =
			{
				sitesList: {sites: [], error:null, loading: false},
				newSite:{site:null, error: null, loading: false},
				activeSite:{site:null, error:null, loading: false},
				deletedSite: {site: null, error:null, loading: false},
			};

export default function reducer(state=INITIAL_STATE, action) {
	let error;
  switch(action.type) {
	case FETCH_SITES:
		// start fetching sites and set loading = true
  	return { ...state, sitesList: {sites:[], error: null, loading: true} };
  case FETCH_SITES_FULFILLED:
		// return list of sites and make loading = false
    return { ...state, sitesList: {sites: action.payload, error:null, loading: false} };
  case FETCH_SITES_REJECTED:
		// return error and make loading = false
		// 2nd one is network or server down errors
    error = action.payload || {message: action.payload.message};
    return { ...state, sitesList: {error: error, loading: false} };
  case RESET_SITES:
		// reset sitesList to initial state
    return { ...state, sitesList: {sites:[], sites: [], error:null, loading: false} };

  case FETCH_SITE:
    return { ...state, activeSite:{...state.activeSite, loading: true}};
  case FETCH_SITE_FULFILLED:
    return { ...state, activeSite: {site: action.payload, error:null, loading: false}};
  case FETCH_SITE_REJECTED:
    error = action.payload || {message: action.payload.message};
    return { ...state, activeSite: {site: null, error:error, loading:false}};
  case RESET_ACTIVE_SITE:
    return { ...state, activeSite: {site: null, error:null, loading: false}};

  case CREATE_SITE:
  	return {...state, newSite: {...state.newSite, loading: true}}
  case CREATE_SITE_FULFILLED:
  	return {...state, newSite: {site:action.payload, error:null, loading: false}}
  case CREATE_SITE_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, newSite: {site:null, error:error, loading: false}}
  case RESET_NEW_SITE:
  	return {...state,  newSite:{site:null, error:null, loading: false}}


  case DELETE_SITE:
   	return {...state, deletedSite: {...state.deletedSite, loading: true}}
  case DELETE_SITE_FULFILLED:
  	return {...state, deletedSite: {site:action.payload, error:null, loading: false}}
  case DELETE_SITE_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, deletedSite: {site:null, error:error, loading: false}}
  case RESET_DELETED_SITE:
  	return {...state,  deletedSite:{site:null, error:null, loading: false}}

  case VALIDATE_SITE_FIELDS:
    return {...state, newSite:{...state.newSite, error: null, loading: true}}
  case VALIDATE_SITE_FIELDS_FULFILLED:
    return {...state, newSite:{...state.newSite, error: null, loading: false}}
  case VALIDATE_SITE_FIELDS_REJECTED:
    let result = action.payload;
    if(!result) {
      error = {message: action.payload.message};
    } else {
      error = {title: result.title, categories: result.categories, description: result.description};
    }
    return {...state, newSite:{...state.newSite, error: error, loading: false}}
  case RESET_SITE_FIELDS:
    return {...state, newSite:{...state.newSite, error: null, loading: null}}
  default:
    return state;
  }
}
