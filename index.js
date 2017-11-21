import React, { Component } from "react";
import PropTypes from "prop-types";
import {
  NativeModules,
  requireNativeComponent,
  View,
  Text,
  LayoutAnimation,
} from "react-native";

const { RNChannel } = NativeModules;
const { UIManager } = NativeModules;

UIManager.setLayoutAnimationEnabledExperimental &&
  UIManager.setLayoutAnimationEnabledExperimental(true);

const defaultStyles = {
  channel: {
    flex: 0,
    position: "absolute",
    width: 330,
    height: 500,
    right: 0,
    bottom: 4,
  },
};

const initialStyles = {
  channel: {
    flex: 0,
    position: "absolute",
    width: 330,
    height: 501,
    right: 0,
    bottom: 4,
  },
};

class ChannelViewComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      update: false,
    };
  }

  componentDidMount() {
    const timoutId = setInterval(() => {
      this.setState({
        update: !this.state.update,
      });
    }, 1000);
    this.setState({ timoutId });
  }

  componentWillUnmount() {
    clearInterval(this.state.timoutId);
  }

  render() {
    // LayoutAnimation.spring();
    return (
      <ChannelView
        accessibilityLabel={"Button"}
        accessibilityComponentType={"button"}
        accessible
        importantForAccessibility={"yes"}
        style={
          this.state.update ? initialStyles.channel : defaultStyles.channel
        }
        {...this.props}
      />
    );
  }
}

ChannelViewComponent.propTypes = {
  ...View.propTypes,
};

const ChannelView = requireNativeComponent("ChannelView", ChannelViewComponent);

export default RNChannel;
export { ChannelViewComponent as ChannelView };
