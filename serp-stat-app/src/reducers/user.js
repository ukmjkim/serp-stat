import * as types from '../constants/user'

const INITIAL_STATE =
			{ activeUser:{user:null, error:null, loading: false} };

export default function reducer(state=INITIAL_STATE, action) {
	let error;
  switch(action.type) {
	case types.FETCH_USER:
    return { ...state, activeUser:{...state.activeUser, loading: true}};
  case types.FETCH_USER_FULFILLED:
    return { ...state, activeUser: {user: action.payload, error:null, loading: false}};
  case types.FETCH_USER_REJECTED:
    error = action.payload || {message: action.payload.message};
    return { ...state, activeUser: {user: null, error:error, loading:false}};
  case types.RESET_ACTIVE_USER:
    return { ...state, activeUser: {user: null, error:null, loading: false}};
	default:
    return state;
  }
}

/*
export default function reducer(state={
  user: {
    id: null,
    login: null,
    nicename: null
  },
  sites: [],
  fetching: false,
  fetched: false,
  error: null,
}, action) {
  switch (action.type) {
    case "FETCH_USER_SITES_USER": {
      return {...state, fetching: true}
    }
    case "FETCH_USER_SITES_REJECTED": {
      return {
        ...state,
        fetching: false,
        error: action.payload
      }
    }
    case "FETCH_USER_SITES_FULFILLED": {
      return {
        ...state,
        user: {
          id: action.payload.id,
          login: action.payload.login,
          nicename: action.payload.niceName
        },
        sites: action.payload.sites,
        fetching: false,
        fetched: true,
      }
    }
    case "SET_USER": {
      return {
        ...state,
        user: {
          id: action.payload,
          title: null,
          url: null
        },
        sites: []
      }
    }
  }

  return state
}
*/
