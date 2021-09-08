import React, { Component } from "react";

class StockRow extends Component {
    constructor(props) {
        super(props);
        this.handleRowClick = this.handleRowClick.bind(this);
    }

    handleRowClick = () => {
        window.open("https://robinhood.com/stocks/"+this.props.relatables[this.props.candidate.stockId].ticker,
                '_blank');
    };

    render() {
        let candidate = this.props.candidate;
        let stock = this.props.relatables[candidate.stockId];

        let marketCap = stock.marketCap;
        let amountLabel;

        if (marketCap>=1000000) {
            marketCap = marketCap / 1000000;
            amountLabel = "T"
        } else if (marketCap>=1000) {
            marketCap = marketCap / 1000;
            amountLabel = "B"
        } else {
            amountLabel = "M"
        }

        let marketCapString = marketCap.toPrecision(3) + " " + amountLabel;

        let slope = candidate.slope.toFixed(3);
        let slopeStyle = "";
        if (slope>0) {
            slopeStyle="#198754";
        } else if (slope < 0) {
            slopeStyle="#DE0202";
        } else {
            slopeStyle="#FFFFFF";
        }

        return (
            <tr
                className={"clickable"}
                onClick={this.handleRowClick}
            >
                <td className={"candidates-table-first-column"}>{stock.ticker}</td>
                <td className={"candidates-table-column"}>{stock.name}</td>
                <td className={"candidates-table-column"} style={{color: slopeStyle}}>{slope}%</td>
                <td className={"candidates-table-column"}>{candidate.length}</td>
                <td className={"candidates-table-column"}>{marketCapString}</td>
                {/*<td className={"candidates-table-column"}>{candidate.rebounds}</td>*/}
            </tr>
        );
    }
}

export default StockRow;
