import * as React from 'react';
import axios from "axios";
import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'
import MockAdapter  from 'axios-mock-adapter';

import * as actions from '../../src/actions/user'
import * as types from '../../src/constants/user'
import { expect } from 'chai'

const middlewares = [thunk]
const mockStore = configureMockStore(middlewares)

describe('actions', () => {
	let mock;

	beforeEach(function() {
    mock = new MockAdapter(axios);
  });

  it('should create an action to reset active user', () => {
    const expectedAction = {
      type: types.RESET_ACTIVE_USER
    }
    expect(actions.resetActiveUser().type).to.equal(types.RESET_ACTIVE_USER)
  });

	it('creates FETCH_USER when fetching user has been done', (done) => {
    mock
			.onGet('/serp-stat-restapi/user/1')
			.reply(function() {
				return new Promise(function(resolve, reject) {
          resolve([200, { user: 'do something' }]);
        });
			});

		const store = mockStore({ id: 1, login: "mjkim" })
		store.dispatch(actions.fetchUser(1)).then(() => {
			console.log("returning in dispatch test")
			let expectedActions = [{
				type: types.FETCH_USER_FULFILLED,
				payload: { user: 'do something' }
			}]
			expect(store.getActions()).to.equal(expectedActions);
		});
		setTimeout(()=>{
	    done();
	  },1000)
	});
})
