import { Component } from "react";
import { Container, Navbar } from "react-bootstrap";

class Header extends Component {
    render() {
        return  (
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand>
                        HY-notice
                    </Navbar.Brand>
                </Container>
            </Navbar>
        );
    }
}

export default Header;