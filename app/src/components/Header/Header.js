import React, { Component } from "react";
import Button from 'react-bootstrap/Button';
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import Nav from "react-bootstrap/Nav";
import AuthModal from "./AuthModal";
import DeleteAccountModal from "./DeleteAccountModal";
import axios from "axios";

class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showAuthModal: false,
            isRegistrationModal: true,
            showDeleteAccountModal: false
        };
        this.openAuthModal = this.openAuthModal.bind(this);
        this.closeAuthModal = this.closeAuthModal.bind(this);
        this.logout = this.logout.bind(this);
        this.openDeleteAccountModal = this.openDeleteAccountModal.bind(this);
        this.closeDeleteAccountModal = this.closeDeleteAccountModal.bind(this);
    }

    openAuthModal = (e) => {
        let register = false;
        if (e.currentTarget.value === 'register') {
            register = true;
        }

        this.setState({showAuthModal: true, isRegistrationModal: register});
    };

    closeAuthModal = () => {
        this.setState({showAuthModal: false});
    };

    logout = () => {
        axios
            .get("/logout")
            .catch((err) => console.log(err))
            .finally(() => this.props.clearUser());
    };

    openDeleteAccountModal = () => {
        this.setState({showDeleteAccountModal: true});
    };

    closeDeleteAccountModal = () => {
        this.setState({showDeleteAccountModal: false});
    };

    render() {
        return (
            <header>
                <div id={"app-header"}>
                    <div id={"app-name"}>
                        <h1><a
                            href={"#"}
                            id={"logo-link"}
                        >
                            Trade Alert
                        </a></h1>
                    </div>
                    {!this.props.loggedIn ?
                        <div id={"auth"}>
                            <div id={"signup-button"} className={"auth-button"}>
                                <Button variant={"outline-success"} value={'register'} onClick={this.openAuthModal}>
                                    Sign up
                                </Button>
                            </div>
                            <div id={"login-button"} className={"auth-button"}>
                                <Button variant={"success"} value={'login'} onClick={this.openAuthModal}>
                                    Log in
                                </Button>
                            </div>
                            <AuthModal
                                showAuthModal={this.state.showAuthModal}
                                isRegistrationModal={this.state.isRegistrationModal}
                                setUser={this.props.setUser}
                                openAuthModal={this.openAuthModal}
                                closeAuthModal={this.closeAuthModal}
                            />
                        </div> :
                        <div id={"auth"}>
                            <div id={"account-button"}>
                                {/*<h4 className={"clickable"} onClick={this.logout}>Account</h4>*/}
                                <Navbar id={"account-navbar"} variant="dark" expand="lg">
                                    <Navbar.Toggle aria-controls="account-navbar" />
                                    <Navbar.Collapse id={"account-navbar-collapse"}>
                                        <Nav>
                                            <NavDropdown
                                                id={"account-dropdown"}
                                                title="Account"
                                                menuVariant="dark"
                                            >
                                                <NavDropdown.Item className={"account-dropdown-item"} onClick={this.logout}>
                                                    Log out
                                                </NavDropdown.Item>
                                                <NavDropdown.Item
                                                    id={"delete-account-button"}
                                                    className={"account-dropdown-item"}
                                                    onClick={this.openDeleteAccountModal}
                                                >
                                                    Delete Account
                                                </NavDropdown.Item>
                                            </NavDropdown>
                                        </Nav>
                                    </Navbar.Collapse>
                                </Navbar>
                                <DeleteAccountModal
                                    showDeleteAccountModal={this.state.showDeleteAccountModal}
                                    closeDeleteAccountModal={this.closeDeleteAccountModal}
                                    clearUser={this.props.clearUser}
                                />
                            </div>
                            {/*<div id={"logout-button"} className={"auth-button"}>*/}
                            {/*    <Button variant={"success"} value={'logout'} onClick={this.logout}>*/}
                            {/*        Log out*/}
                            {/*    </Button>*/}
                            {/*</div>*/}
                        </div>
                    }
                </div>
            </header>
        );
    }
}

export default Header;
