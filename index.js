import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { NativeModules, requireNativeComponent, View } from 'react-native';

const { RNChannel } = NativeModules;


const defaultStyles = {
  channel: {
    flex: 0,
    // position: "absolute",
    width: 100,
    height: 100,
    right: 0,
    bottom: 40,
  },
};

class ChannelViewComponent extends Component {
  render() {
    return (
        <ChannelView
          style={defaultStyles.channel}
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
