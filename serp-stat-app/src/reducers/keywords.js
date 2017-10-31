import {
	FETCH_KEYWORDS, FETCH_KEYWORDS_FULFILLED, FETCH_KEYWORDS_REJECTED, RESET_KEYWORDS,
	FETCH_PAGINATED_KEYWORDS, FETCH_PAGINATED_KEYWORDS_FULFILLED, FETCH_PAGINATED_KEYWORDS_REJECTED, RESET_PAGINATED_KEYWORDS,
	FETCH_KEYWORDS_COUNT, FETCH_KEYWORDS_COUNT_FULFILLED, FETCH_KEYWORDS_COUNT_REJECTED, RESET_KEYWORDS_COUNT,
	FETCH_KEYWORD, FETCH_KEYWORD_FULFILLED, FETCH_KEYWORD_REJECTED, RESET_ACTIVE_KEYWORD,
	CREATE_KEYWORD, CREATE_KEYWORD_FULFILLED,  CREATE_KEYWORD_REJECTED, RESET_NEW_KEYWORD,
	VALIDATE_KEYWORD_FIELDS, VALIDATE_KEYWORD_FIELDS_FULFILLED, VALIDATE_KEYWORD_FIELDS_REJECTED, RESET_KEYWORD_FIELDS,
  DELETE_KEYWORD, DELETE_KEYWORD_FULFILLED, DELETE_KEYWORD_REJECTED, RESET_DELETED_KEYWORD
} from '../actions/keywords';

const INITIAL_STATE =
			{
				keywordsList: {keywords: null, error:null, loading: false},
				keywordsPaginated: {keywords: null, error:null, loading: false},
				keywordsCount: {count: null, error:null, loading: false},
				newKeyword:{keyword:null, error: null, loading: false},
				activeKeyword:{keyword:null, error:null, loading: false},
				deletedKeyword: {keyword: null, error:null, loading: false},
			};

export default function reducer(state=INITIAL_STATE, action) {
	let error;
  switch(action.type) {
	case FETCH_KEYWORDS:
		// start fetching keywords and set loading = true
  	return { ...state, keywordsList: {keywords:null, error: null, loading: true} };
  case FETCH_KEYWORDS_FULFILLED:
		// return list of keywords and make loading = false
    return { ...state, keywordsList: {keywords: action.payload, error:null, loading: false} };
  case FETCH_KEYWORDS_REJECTED:
		// return error and make loading = false
		// 2nd one is network or server down errors
    error = action.payload || {message: action.payload.message};
    return { ...state, keywordsList: {keywords: null, error: error, loading: false} };
  case RESET_KEYWORDS:
		// reset keywordsList to initial state
    return { ...state, keywordsList: {keywords: null, error:null, loading: false} };

	case FETCH_PAGINATED_KEYWORDS:
  	return { ...state, keywordsPaginated: {keywords:null, error: null, loading: true} };
  case FETCH_PAGINATED_KEYWORDS_FULFILLED:
    return { ...state, keywordsPaginated: {keywords: action.payload, error:null, loading: false} };
  case FETCH_PAGINATED_KEYWORDS_REJECTED:
    error = action.payload || {message: action.payload.message};
    return { ...state, keywordsPaginated: {keywords: null, error: error, loading: false} };
  case RESET_PAGINATED_KEYWORDS:
    return { ...state, keywordsPaginated: {keywords: null, error:null, loading: false} };

	case FETCH_KEYWORDS_COUNT:
  	return { ...state, keywordsCount: {count:null, error: null, loading: true} };
  case FETCH_KEYWORDS_COUNT_FULFILLED:
    return { ...state, keywordsCount: {count: action.payload, error:null, loading: false} };
  case FETCH_KEYWORDS_COUNT_REJECTED:
    error = action.payload || {message: action.payload.message};
    return { ...state, keywordsCount: {count: null, error: error, loading: false} };
  case RESET_KEYWORDS_COUNT:
    return { ...state, keywordsCount: {count: null, error:null, loading: false} };

  case FETCH_KEYWORD:
    return { ...state, activeKeyword:{...state.activeKeyword, loading: true}};
  case FETCH_KEYWORD_FULFILLED:
    return { ...state, activeKeyword: {keyword: action.payload, error:null, loading: false}};
  case FETCH_KEYWORD_REJECTED:
    error = action.payload || {message: action.payload.message};
    return { ...state, activeKeyword: {keyword: null, error:error, loading:false}};
  case RESET_ACTIVE_KEYWORD:
    return { ...state, activeKeyword: {keyword: null, error:null, loading: false}};

  case CREATE_KEYWORD:
  	return {...state, newKeyword: {...state.newKeyword, loading: true}}
  case CREATE_KEYWORD_FULFILLED:
  	return {...state, newKeyword: {keyword:action.payload, error:null, loading: false}}
  case CREATE_KEYWORD_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, newKeyword: {keyword:null, error:error, loading: false}}
  case RESET_NEW_KEYWORD:
  	return {...state,  newKeyword:{keyword:null, error:null, loading: false}}


  case DELETE_KEYWORD:
   	return {...state, deletedKeyword: {...state.deletedKeyword, loading: true}}
  case DELETE_KEYWORD_FULFILLED:
  	return {...state, deletedKeyword: {keyword:action.payload, error:null, loading: false}}
  case DELETE_KEYWORD_REJECTED:
    error = action.payload || {message: action.payload.message};
  	return {...state, deletedKeyword: {keyword:null, error:error, loading: false}}
  case RESET_DELETED_KEYWORD:
  	return {...state,  deletedKeyword:{keyword:null, error:null, loading: false}}

  case VALIDATE_KEYWORD_FIELDS:
    return {...state, newKeyword:{...state.newKeyword, error: null, loading: true}}
  case VALIDATE_KEYWORD_FIELDS_FULFILLED:
    return {...state, newKeyword:{...state.newKeyword, error: null, loading: false}}
  case VALIDATE_KEYWORD_FIELDS_REJECTED:
    let result = action.payload;
    if(!result) {
      error = {message: action.payload.message};
    } else {
      error = {title: result.title, categories: result.categories, description: result.description};
    }
    return {...state, newKeyword:{...state.newKeyword, error: error, loading: false}}
  case RESET_KEYWORD_FIELDS:
    return {...state, newKeyword:{...state.newKeyword, error: null, loading: null}}
  default:
    return state;
  }
}
