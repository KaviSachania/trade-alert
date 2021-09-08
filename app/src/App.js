import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import Header from './components/Header/Header';
import Home from './components/Main/Home';
import axios from "axios";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loggedIn: false,
            loaded: false
        }
    }

    componentDidMount() {
        this.checkLoggedIn();
    }

    setUser = () => {
        this.setState({loggedIn: true});
    };

    clearUser = () => {
        this.setState({user: null, loggedIn: false});
    };

    checkLoggedIn = async () => {
        await axios
            .get("/loggedin/")
            .then(() => this.setUser())
            .catch((err) => console.log(err))
            .finally(() => this.setState({loaded: true}));
    };

    render() {
        if (this.state.loaded) {
            return (
                <div className="App">
                    <Header
                        loggedIn={this.state.loggedIn}
                        user={this.state.user}
                        setUser={this.setUser}
                        clearUser={this.clearUser}
                    />
                    <Router>
                        <Switch>
                            <Route path="/" exact component={
                                () =>
                                    <Home loggedIn={this.state.loggedIn}/>
                            }
                            />
                        </Switch>
                    </Router>
                </div>
            );
        } else {
            return null;
        }
    }
}

export default App;
