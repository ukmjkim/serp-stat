export default function reducer(state={
  sites: [],
  fetching: false,
  fetched: false,
  error: null,
}, action) {
    console.log("action.type" + action.type);
  switch (action.type) {


    case "FETCH_SITES": {
      return {...state, fetching: true}
    }
    case "FETCH_SITES_REJECTED": {
      return {...state, fetching: false, error: action.payload}
    }
    case "FETCH_SITES_FULFILLED": {
      return {
        ...state,
        fetching: false,
        fetched: true,
        sites: action.payload,
      }
    }
  }

  return state
}
