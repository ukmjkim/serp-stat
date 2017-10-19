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
console.log("action.type" + action.type);
  switch (action.type) {
    case "FETCH_USER": {
      return {...state, fetching: true}
    }
    case "FETCH_USER_REJECTED": {
      return {
        ...state,
        fetching: false,
        error: action.payload
      }
    }
    case "FETCH_USER_FULFILLED": {
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
  }

  return state
}
