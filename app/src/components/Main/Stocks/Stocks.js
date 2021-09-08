import React, { Component } from "react";

import StocksHeader from "./StocksHeader";
import StocksSelection from "./StocksSelection";
import StocksTable from "./StocksTable";

class Stocks extends Component {
    render() {
        return (
            <div id={"stocks"}>
                <div id={"top-space"}>

                </div>
                <StocksHeader/>
                {/*<StocksSelection/>*/}
                <StocksTable/>
            </div>
        );
    }
}

export default Stocks;
