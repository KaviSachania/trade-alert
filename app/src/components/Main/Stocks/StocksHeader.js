import React, { Component } from "react";

class StocksHeader extends Component {
    render() {
        return (
            <div id={"candidates-header"}>
                <h2>Support Stocks</h2>
                <div id={"candidates-header-byline"}>
                    <p>
                        Each stock below is currently priced at a support level that has been tested multiple times.
                        {/*over the listed number of days.*/}
                    </p>
                </div>
            </div>
        );
    }
}

export default StocksHeader;
