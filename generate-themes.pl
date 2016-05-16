#!/usr/bin/perl
use strict;

use constant OUTDIR => './res/values-v21/';

my $THEMES = {
	standard => {
		light => { colorAccent => '#ff3e677a', colorPrimary => '#ff37474f', colorPrimaryDark => '#ff263238'},
		dark  => { colorAccent => '#ff3e677a', colorPrimary => '#ff37474f', colorPrimaryDark => '#ff263238'},
	},
};


foreach my $theme_name (keys(%$THEMES)) {
	my $theme_id = ($theme_name eq 'standard' ? '' : ucfirst($theme_name)."."); # standard has no prefix
	my $outfile  = OUTDIR."/theme-$theme_name.xml";
	my $outbuff  = get_xml($THEMES->{$theme_name}, $theme_id);

	open(OUT, ">", $outfile) or die "Can not write to $outfile: $!\n";
	print OUT $outbuff;
	close(OUT);
}



sub get_xml {
	my($this, $tid) = @_;

my $DATA = << "EOF";
<?xml version="1.0" encoding="utf-8"?>
<!--
Copyright (C) 2015 Adrian Ulrich <adrian\@blinkenlights.ch>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program. If not, see <http://www.gnu.org/licenses/>. 
-->

<resources>
	<style name="${tid}VanillaBase" parent="android:Theme.Material.Light.DarkActionBar">
		<item name="overlay_background_color">\@color/overlay_background_light</item>
		<item name="overlay_foreground_color">\@color/overlay_foreground_light</item>
		<item name="float_color">\@color/material_grey_400</item>
		<item name="tabs_background">$this->{light}->{colorPrimary}</item>
		<item name="now_playing_marker">$this->{light}->{colorAccent}</item>
		<item name="controls_normal">\@color/material_grey_600</item>
		<item name="controls_active">$this->{light}->{colorAccent}</item>
		<item name="android:colorAccent">$this->{light}->{colorAccent}</item>
		<item name="android:colorPrimary">$this->{light}->{colorPrimary}</item>
		<item name="android:colorPrimaryDark">$this->{light}->{colorPrimaryDark}</item>
	</style>

	<style name="${tid}Playback" parent="${tid}VanillaBase">
		<item name="android:actionBarStyle">\@style/Universal.PlaybackActionBar</item>
	</style>

	<style name="${tid}BackActionBar" parent="${tid}VanillaBase">
		<item name="android:actionBarStyle">\@style/Universal.PlaybackActionBar</item>
	</style>

	<style name="${tid}Library" parent="${tid}VanillaBase">
		<item name="android:windowActionBar">false</item>
		<item name="android:windowNoTitle">true</item>
	</style>

	<!-- dark theme -->
	<style name="${tid}Dark.VanillaBase" parent="android:Theme.Material">
		<item name="overlay_background_color">\@color/overlay_background_dark</item>
		<item name="overlay_foreground_color">\@color/overlay_foreground_dark</item>
		<item name="float_color">\@color/material_grey_900</item>
		<item name="tabs_background">$this->{dark}->{colorPrimary}</item>
		<item name="now_playing_marker">$this->{dark}->{colorAccent}</item>
		<item name="controls_normal">\@color/material_grey_600</item>
		<item name="controls_active">$this->{dark}->{colorAccent}</item>
		<item name="android:colorAccent">$this->{dark}->{colorAccent}</item>
		<item name="android:colorPrimary">$this->{dark}->{colorPrimary}</item>
		<item name="android:colorPrimaryDark">$this->{dark}->{colorPrimaryDark}</item>
	</style>

	<style name="${tid}Dark.Playback" parent="${tid}Dark.VanillaBase">
		<item name="android:actionBarStyle">\@style/Universal.PlaybackActionBar</item>
	</style>

	<style name="${tid}Dark.BackActionBar" parent="${tid}Dark.VanillaBase">
		<item name="android:actionBarStyle">\@style/Universal.PlaybackActionBar</item>
	</style>

	<style name="${tid}Dark.Library" parent="${tid}Dark.VanillaBase">
		<item name="android:windowActionBar">false</item>
		<item name="android:windowNoTitle">true</item>
	</style>
</resources>
EOF
	return $DATA
}
