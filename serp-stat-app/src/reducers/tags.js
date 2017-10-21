export function tagsIsLoading(state = false, action) {
  switch (action.type) {
    case "TAGS_FETCH": {
      return action.isLoading;
    }
    default:
      return state;
  }
}
export function tagsHasErrored(state = false, action) {
  switch (action.type) {
    case "TAGS_FETCH_REJECTED": {
      return action.hasErrored;
    }
    default:
      return state;
  }
}
export function tagsHasFetched(state = false, action) {
  switch (action.type) {
    case "TAGS_FETCH_FULFILLED": {
      return action.hasFetched;
    }
    default:
      return state;
  }
}
export function tags(state = [], action) {
  switch (action.type) {
    case "TAGS_FETCH_RESULT": {
      return action.payload.tags;
    }
    default:
      return state;
  }
}
