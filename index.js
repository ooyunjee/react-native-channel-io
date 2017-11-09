import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { NativeModules, requireNativeComponent, View, Text } from 'react-native';

const { RNChannel } = NativeModules;


const defaultStyles = {
  channel: {
    flex: 0,
    position: "absolute",
    width: 96,
    height: 96,
    right: 0,
    bottom: 4,
  },
};

const initialStyles = {
  channel: {
    flex: 0,
    position: "absolute",
    width: 96,
    height: 95,
    right: 0,
    bottom: 5,
  },
};

class ChannelViewComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      update: false,
    }
  }

  componentDidMount() {
    const timoutId = setTimeout(() => {
      this.setState({
        update: !this.state.update,
      });
    }, 1000);
    this.setState({ timoutId });
  }
  
  componentWillUnmount() {
   clearTimeout(this.state.timoutId);
  }

  render() {
    return (
        <ChannelView
          accessibilityLabel={'Button'}
          accessibilityComponentType={'button'}
          accessible={true}
          importantForAccessibility={'yes'}
          style={(this.state.update) ? initialStyles.channel : defaultStyles.channel}
          {...this.props}
        />
    );
  }
}

ChannelViewComponent.propTypes = {
  ...View.propTypes,
}

const ChannelView = requireNativeComponent('ChannelView', ChannelViewComponent);

export default RNChannel;
export { ChannelViewComponent as ChannelView };
