import {GET_POSTS, GET_ROUTE, CURRENT_USER, NO_CURRENT_USER, UPLOAD_GPX, CREATE_POST} from "../constants/action-types";
import compose from 'redux'

const initialState = {
    currentRoute: [],
    posts: [],
    currentUser: {}
};

function rootReducer(state = initialState, action) {
    switch (action.type) {
        case CURRENT_USER:
            return Object.assign({}, state, {
                currentUser: action.payload
            });
        case NO_CURRENT_USER:
            return Object.assign({}, state, {
                currentUser: action.payload
            })
    }
    return state;
}

export default rootReducer;