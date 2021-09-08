import React, { Component } from "react";
import Stocks from './Stocks/Stocks';
import Alerts from './Alerts/Alerts';

import axios from "axios";


class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            data: 1
        }
    }

    render() {
        let mainDiv = null;
        if (this.state.data != null) {
            mainDiv = (
                <div className={"main"}>
                    <div id={"main-simulation-content"}>
                        <Stocks
                        />
                        <Alerts loggedIn={this.props.loggedIn}
                        />
                    </div>
                </div>
            );
        }

        return mainDiv;
    }
}

export default Home;
