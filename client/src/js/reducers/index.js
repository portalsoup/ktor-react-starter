import {GET_POSTS, GET_ROUTE, CURRENT_USER, UPLOAD_GPX} from "../constants/action-types";
import compose from 'redux'

const initialState = {
    currentRoute: [],
    posts: [],
    currentUser: {}
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
        case CURRENT_USER:
            return Object.assign({}, state, {
                currentUser: action.payload
            });
        case UPLOAD_GPX:
            return state
    }
    return state;
}

export default rootReducer;