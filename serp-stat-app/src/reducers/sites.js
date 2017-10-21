export function sitesIsLoading(state = false, action) {
  switch (action.type) {
    case "SITES_FETCH": {
      return action.isLoading;
    }
    default:
      return state;
  }
}
export function sitesHasErrored(state = false, action) {
  switch (action.type) {
    case "SITES_FETCH_REJECTED": {
      return action.hasErrored;
    }
    default:
      return state;
  }
}
export function sites(state = [], action) {
  switch (action.type) {
    case "SITES_FETCH_FULFILLED": {
      return action.payload.sites;
    }
    default:
      return state;
  }
}
export function sitesSelected(state = null, action) {
  switch (action.type) {
    case "SITES_SELECTED": {
      return action.selectedSite;
    }
    default:
      return state;
  }
}

/*
export default function reducer(state={
  site: {
    id: null,
    title: null,
    url: null
  },
  tags: [],
  fetching: false,
  fetched: false,
  error: null,
}, action) {
    console.log("action.type" + action.type);
  switch (action.type) {
    case "FETCH_SITE_TAGS": {
      return {...state, fetching: true}
    }
    case "FETCH_SITE_TAGS_REJECTED": {
      return {...state, fetching: false, error: action.payload}
    }
    case "FETCH_SITE_TAGS_FULFILLED": {
      return {
        ...state,
        site: {
          id: action.payload.id,
          title: action.payload.title,
          url: action.payload.url
        },
        tags: action.payload.tags,
        fetching: false,
        fetched: true
      }
    }
    case "SET_SITE": {
      return {
        ...state,
        site: {
          id: action.payload,
          title: null,
          url: null
        }
      }
    }
  }

  return state
}
*/
