import {GET_POSTS, GET_ROUTE} from "../constants/action-types";
import compose from 'redux'

const initialState = {
    currentRoute: [],
    posts: []
};

function rootReducer(state = initialState, action) {
    switch (action.type) {
        case GET_ROUTE:
            return Object.assign({}, state, {
                currentRoute: action.payload
        });
        case GET_POSTS:
            return Object.assign({}, state, {
                posts: action.payload
            });
    }
    return state;
}

export default rootReducer;