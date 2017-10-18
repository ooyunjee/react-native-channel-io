import React, { Component } from 'react';
// import PropTypes from 'prop-types';
import { NativeModules, requireNativeComponent, View } from 'react-native';

const { RNChannel } = NativeModules;

class ChannelViewComponent extends Component {
  render() {
    return (
        <ChannelView {...this.props} />
    );
}
const ChannelView = requireNativeComponent('ChannelView', ChannelViewComponent);

export default RNChannel;
export { ChannelViewComponent as ChannelView };