import React, { Component } from "react";
import Table from 'react-bootstrap/Table';
import axios from 'axios';

import StockRow from './StockRow';

class StocksTable extends Component {

    constructor(props) {
        super(props);
        this.state = {
            candidates: [],
            relatables: {},
            tickerSort: 0,
            nameSort: 0,
            slopeSort: 0,
            lengthSort: 0,
            marketCapSort: 0
        };
        this.toggleTickerSort = this.toggleTickerSort.bind(this);
        this.toggleNameSort = this.toggleNameSort.bind(this);
        this.toggleSlopeSort = this.toggleSlopeSort.bind(this);
        this.toggleLengthSort = this.toggleLengthSort.bind(this);
        this.toggleMarketCapSort = this.toggleMarketCapSort.bind(this);
    }

    componentDidMount() {
        this.loadCandidates();
    }

    loadCandidates = async () => {
        await axios
            .get("/candidates/")
            .then((res) => this.setState({candidates: res.data.results, relatables: res.data.relatables}))
            .then(() => console.log(this.state.candidates))
            .catch((err) => console.log(err));
    };

    toggleTickerSort = () => {
        if (this.state.candidates.length !== Object.keys(this.state.relatables).length) {
            return;
        }

        let newTickerSort = this.state.tickerSort * -1;
        if (newTickerSort === 0) {
            newTickerSort = 1;
        }

        let stocksMap = this.state.relatables;
        let stocks = [];
        for (let stock in stocksMap) {
            stocks.push(stocksMap[stock]);
        }

        if (newTickerSort === 1) {
            stocks.sort(function (a, b) {
                if (a.ticker < b.ticker) return -1;
                if (a.ticker > b.ticker) return 1;
                return 0;
            });
        } else if (newTickerSort === -1) {
            stocks.sort(function (a, b) {
                if (a.ticker < b.ticker) return 1;
                if (a.ticker > b.ticker) return -1;
                return 0;
            });
        }

        let stockIndexById = new Map();
        stocks.forEach(function (stock, i) {
            stockIndexById.set(stock.id, i);
        });

        let sortedCandidates = new Array(stocks.length);
        for (let candidate of this.state.candidates) {
            let stockId = candidate.stockId;
            sortedCandidates[stockIndexById.get(stockId)] = candidate;
        }

        this.setState({
            tickerSort: newTickerSort,
            nameSort: 0,
            slopeSort: 0,
            lengthSort: 0,
            marketCapSort: 0,
            candidates: sortedCandidates
        });
    };

    toggleNameSort = () => {
        if (this.state.candidates.length !== Object.keys(this.state.relatables).length) {
            return;
        }

        let newNameSort = this.state.nameSort * -1;
        if (newNameSort === 0) {
            newNameSort = 1;
        }

        let stocksMap = this.state.relatables;
        let stocks = [];
        for (let stock in stocksMap) {
            stocks.push(stocksMap[stock]);
        }

        if (newNameSort === 1) {
            stocks.sort(function (a, b) {
                if (a.name < b.name) return -1;
                if (a.name > b.name) return 1;
                return 0;
            });
        } else if (newNameSort === -1) {
            stocks.sort(function (a, b) {
                if (a.name < b.name) return 1;
                if (a.name > b.name) return -1;
                return 0;
            });
        }

        let stockIndexById = new Map();
        stocks.forEach(function (stock, i) {
            stockIndexById.set(stock.id, i);
        });

        let sortedCandidates = new Array(stocks.length);
        for (let candidate of this.state.candidates) {
            let stockId = candidate.stockId;
            sortedCandidates[stockIndexById.get(stockId)] = candidate;
        }

        this.setState({
            tickerSort: 0,
            nameSort: newNameSort,
            slopeSort: 0,
            lengthSort: 0,
            marketCapSort: 0,
            candidates: sortedCandidates
        });
    };

    toggleSlopeSort = () => {
        let newSlopeSort = this.state.slopeSort * -1;
        if (newSlopeSort === 0) {
            newSlopeSort = 1;
        }

        let candidates = this.state.candidates;

        if (newSlopeSort === 1) {
            candidates.sort(function (a, b) {
                if (a.slope < b.slope) return 1;
                if (a.slope > b.slope) return -1;
                return 0;
            });
        } else if (newSlopeSort === -1) {
            candidates.sort(function (a, b) {
                if (a.slope < b.slope) return -1;
                if (a.slope > b.slope) return 1;
                return 0;
            });
        }

        this.setState({
            tickerSort: 0,
            nameSort: 0,
            slopeSort: newSlopeSort,
            lengthSort: 0,
            marketCapSort: 0,
            candidates: candidates
        });
    };

    toggleLengthSort = () => {
        let newLengthSort = this.state.lengthSort * -1;
        if (newLengthSort === 0) {
            newLengthSort = 1;
        }

        let candidates = this.state.candidates;

        if (newLengthSort === 1) {
            candidates.sort(function (a, b) {
                if (a.length < b.length) return 1;
                if (a.length > b.length) return -1;
                return 0;
            });
        } else if (newLengthSort === -1) {
            candidates.sort(function (a, b) {
                if (a.length < b.length) return -1;
                if (a.length > b.length) return 1;
                return 0;
            });
        }

        this.setState({
            tickerSort: 0,
            nameSort: 0,
            slopeSort: 0,
            lengthSort: newLengthSort,
            marketCapSort: 0,
            candidates: candidates
        });
    };

    toggleMarketCapSort = () => {
        console.log(this.state.candidates.length);
        console.log(Object.keys(this.state.relatables).length);

        if (this.state.candidates.length !== Object.keys(this.state.relatables).length) {
            return;
        }

        let newMarketCapSort = this.state.marketCapSort * -1;
        if (newMarketCapSort === 0) {
            newMarketCapSort = 1;
        }

        let stocksMap = this.state.relatables;
        let stocks = [];
        for (let stock in stocksMap) {
            stocks.push(stocksMap[stock]);
        }

        if (newMarketCapSort === 1) {
            stocks.sort(function (a, b) {
                if (a.marketCap < b.marketCap) return 1;
                if (a.marketCap > b.marketCap) return -1;
                return 0;
            });
        } else if (newMarketCapSort === -1) {
            stocks.sort(function (a, b) {
                if (a.marketCap < b.marketCap) return -1;
                if (a.marketCap > b.marketCap) return 1;
                return 0;
            });
        }

        let stockIndexById = new Map();
        stocks.forEach(function (stock, i) {
            stockIndexById.set(stock.id, i);
        });

        let sortedCandidates = new Array(stocks.length);
        for (let candidate of this.state.candidates) {
            let stockId = candidate.stockId;
            sortedCandidates[stockIndexById.get(stockId)] = candidate;
        }

        this.setState({
            tickerSort: 0,
            nameSort: 0,
            slopeSort: 0,
            lengthSort: 0,
            marketCapSort: newMarketCapSort,
            candidates: sortedCandidates
        });
    };

    render() {
        // let stockRows = [];
        // for (let candidateI = 0; candidateI < this.state.candidates.length; candidateI++) {
        //     stockRows.push(<StockRow
        //         candidate={this.state.candidates[candidateI]}
        //         relatables={this.state.relatables}
        //     />)
        // }

        if (this.state.candidates.length === 0) {
            return null;
        }

        let relatables = this.state.relatables;

        let tickerStyle = "";
        if (this.state.tickerSort !== 0) {
            tickerStyle = "#198754";
        }

        let nameStyle = "";
        if (this.state.nameSort !== 0) {
            nameStyle = "#198754";
        }

        let slopeStyle = "";
        if (this.state.slopeSort !== 0) {
            slopeStyle = "#198754";
        }

        let lengthStyle = "";
        if (this.state.lengthSort !== 0) {
            lengthStyle = "#198754";
        }

        let marketCapStyle = "";
        if (this.state.marketCapSort !== 0) {
            marketCapStyle = "#198754";
        }

        return (
            <div>
                <Table id={"candidates-table"} align={"left"}>
                    <thead>
                        <tr className={"candidates-table-header-row"}>
                            <th
                                className={"candidates-table-first-column clickable sortable"}
                                style={{color:tickerStyle}}
                                onClick={this.toggleTickerSort}
                            >
                                Ticker
                            </th>
                            <th
                                className={"candidates-table-column clickable sortable"}
                                style={{color:nameStyle}}
                                onClick={this.toggleNameSort}
                            >
                                Name
                            </th>
                            <th
                                className={"candidates-table-column clickable sortable"}
                                style={{color:slopeStyle}}
                                onClick={this.toggleSlopeSort}
                            >
                                Avg. Weekly +/-
                            </th>
                            <th
                                className={"candidates-table-column clickable sortable"}
                                style={{color:lengthStyle}}
                                onClick={this.toggleLengthSort}
                            >
                                Days
                            </th>
                            <th
                                className={"candidates-table-column clickable sortable"}
                                style={{color:marketCapStyle}}
                                onClick={this.toggleMarketCapSort}>
                                Market Cap.
                            </th>
                            {/*<th className={"candidates-table-column clickable"}>*/}
                            {/*    Tests*/}
                            {/*</th>*/}
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.candidates.map(function(candidate, i){
                            return <StockRow
                                candidate={candidate}
                                relatables={relatables}
                                key={i}
                            />;
                        })}
                        {/*{stockRows}*/}
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default StocksTable;
