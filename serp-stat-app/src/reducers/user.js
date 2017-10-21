export function userIsLoading(state = false, action) {
  switch (action.type) {
    case "USER_FETCH": {
      return action.isLoading;
    }
    default:
      return state;
  }
}
export function userHasErrored(state = false, action) {
  switch (action.type) {
    case "USER_FETCH_REJECTED": {
      return action.hasErrored;
    }
    default:
      return state;
  }
}
export function user(state = {
  id: null,
  login: null,
  nicename: null
}, action) {
  switch (action.type) {
      case "USER_FETCH_FULFILLED": {
        return action.payload;
      }
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
