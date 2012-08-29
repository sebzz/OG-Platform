/*
 * Copyright 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * Please see distribution for license.
 */
$.register_module({
    name: 'og.common.gadgets.GadgetsContainer',
    dependencies: ['og.common.gadgets.manager', 'og.api.text'],
    obj: function () {
        var api = og.api, tabs_template, overflow_template, dropbox_template, counter = 1,
            header = ' .ui-layout-header';
        return function (selector_prefix, pane) {
            var initialized = false, loading, gadgets = [], container = this, selector = selector_prefix + pane,
                class_prefix = selector_prefix.substring(1),
                live_id, // active tab id
                overflow = {}, // document offet of overflow panel
                $overflow_panel; // panel that houses non visible tabs
            var extract_id = function (str) {return +str.replace(/^og\-tab\-(\d+)\s(?:.*)$/, '$1');};
            var extract_index = function (id) {
                return gadgets.reduce(function (acc, val, idx) {return acc + (val.id === id ? idx : 0);}, 0)
            };
            /**
             * @param {Number|Null} id
             *        if id is a Number set the active tab to that ID
             *        if id is undefined use the current Live ID and reflow tabs
             *        if id is null set tabs to a single empty tab
             */
            var update_tabs = function (id) {
                var $header = $(selector + header), tabs;
                /**
                 * @param id Id of gadget to show, hide all others
                 */
                var show_gadget = function (id) {
                    $(selector).find('.OG-gadget-container [class*=OG-gadget-]').hide()
                        .filter('.OG-gadget-' + id).show();
                    live_id = id;
                };
                /** Manages the widths of the tabs when the panel is resized, the following stages exist:
                 *  0 - all tabs are full size
                 *  1 - all but the active tab are truncated
                 *  2 - tabs are truncated plus some/all are moved to an overflow panel
                 *  3 - same as above with active tab being truncated
                 */
                var reflow = function () {
                    var overflow_buffer = 25, // space for the overflow button
                        buttons_buffer = {'south': 23}, // add extra buffer space if the tabs area has buttons
                        min_tab_width = 50,
                        $tabs_container = $(selector + ' .OG-gadget-tabs'),
                        $tabs = $tabs_container.find('li[class^=og-tab-]'),
                        $active_tab = $(selector + ' .og-active'),
                        $overflow_button = $(selector + ' .og-overflow'),
                        active_tab_width = $active_tab.outerWidth(),
                        num_tabs = $tabs.length,
                        num_inactive_tabs = num_tabs - 1,
                        new_tab_width, // new truncated width of a tab
                        compressed_tabs_width, // new full width of all tabs
                        space_needed, // number of pixles needed to reclaim
                        num_tabs_to_hide, // number of tabs to move to overflow
                        $tabs_to_move, // the actual tabs we are moving
                        // full available width
                        full_width = $tabs_container.width() - (overflow_buffer + (buttons_buffer[pane] || 0)),
                        // the full width of all the tabs
                        tabs_width = Array.prototype.reduce.apply($tabs
                            .map(function () {return $(this).outerWidth();}), [function (a, b) {return a + b;}, 0]),
                        new_window = function (i, dropped) {
                            var prefix = 'gadget.ftl#/gadgetscontainer/', url,
                                index = extract_index(extract_id($(this).attr('class'))),
                                id = gadgets[index].config.options.id,
                                type = gadgets[index].type;
                            switch (type) {
                                case 'timeseries': url = prefix + type + ':' + id; break;
                                case 'grid': url = prefix + type + ':' + id; break;
                            }
                            if (!dropped) {
                                window.open(url);
                                setTimeout(container.del.partial(gadgets[i]));
                            }
                        };
                    // stage 1
                    if (tabs_width > full_width) {
                        new_tab_width = ~~((full_width - active_tab_width) / num_inactive_tabs);
                        new_tab_width = new_tab_width < min_tab_width ? min_tab_width : new_tab_width;
                        compressed_tabs_width = (num_inactive_tabs * new_tab_width) + active_tab_width;
                        // stage 2
                        if (compressed_tabs_width > full_width) {
                            if (num_tabs > 1) {
                                $overflow_button.show().on('click', function (e) {
                                    e.stopPropagation();
                                    $overflow_panel.toggle();
                                    var wins = [window].concat(Array.prototype.slice.call(window.frames));
                                    if ($overflow_panel.is(":visible")) $(wins).on('click.overflow_handler', function () {
                                        $overflow_panel.hide();
                                        $(wins).off('click.overflow_handler');
                                    });
                                    else $(wins).off('click.overflow_handler');
                                });
                            }
                            space_needed = full_width - compressed_tabs_width;
                            num_tabs_to_hide = Math.ceil(Math.abs(space_needed) / new_tab_width);
                            if (num_tabs_to_hide) $overflow_panel.find('ul').html(
                                $tabs_to_move = $tabs.filter(':not(.og-active):lt(' + num_tabs_to_hide + ')')
                            );
                            // stage 3
                            if (num_tabs_to_hide >= num_tabs) $active_tab.width(
                                active_tab_width
                                + (space_needed + ((num_inactive_tabs * min_tab_width) - overflow_buffer))
                                + 'px'
                            );
                        } else {
                            $overflow_panel.hide();
                        }
                        // set inactive tab widths to calculated value
                        $tabs.each(function () {if (!$(this).hasClass('og-active')) $(this).outerWidth(new_tab_width)});
                        // unset width of tabs in overflow panel
                        if ($tabs_to_move) $tabs_to_move.each(function () {$(this).attr('style', '');});
                        // set position of overflow panel
                        overflow.right = $(document).width() - ($overflow_button.offset().left + 25 - 5);
                        overflow.height = $overflow_button.height();
                        overflow.top = $overflow_button.offset().top + overflow.height + 1;
                        $overflow_panel.css({'right': overflow.right + 'px', 'top': overflow.top + 'px'});
                        $tabs.each(function () { // add tooltips to truncated tabs only
                            var $this = $(this);
                            if (!!$this.attr('style')) $this.attr('title', $this.text().trim());
                        });
                    }
                    // implement drag
                    $tabs.each(function (i) {
                        $(this).draggable({
                            cursor: 'move', zIndex: 3, cursorAt: {top: 25}, scroll: false,
                            iframeFix: true, appendTo: 'body', distance: 20,
                            revert: new_window.partial(i),
                            stop: function () {$(this).draggable('option','revert', new_window.partial(i));},
                            helper: function() {return dropbox_template({label: $(this).text().trim()});}
                        }).data({gadget: gadgets[i], handler: function () {container.del(gadgets[i]);}});
                    });
                };
                if (id === null) $header.html(tabs_template({'tabs': [{'name': 'empty'}]})); // empty tabs
                else {
                    if (id === void 0) id = live_id;
                    tabs = gadgets.reduce(function (acc, val) {
                        return acc.push({
                            'name': val.config.name, 'active': (id === val.id), 'delete': true, 'id': val.id
                        }) && acc;
                    }, []);
                    $header.html(tabs_template({'tabs': tabs}));
                    reflow();
                    show_gadget(id);
                }
            };
            container.init = function (arr) {
                var toggle_dropbox = function () {
                        var $db = $('.og-drop').length, $dbs_span = $('.OG-dropbox span');
                        if ($db) $dbs_span.removeClass('og-icon-new-window').addClass('og-icon-drop');
                        else $dbs_span.removeClass('og-icon-drop').addClass('og-icon-new-window');
                    };
                loading = true;
                $.when(
                    api.text({module: 'og.analytics.tabs_tash'}),
                    api.text({module: 'og.analytics.tabs_overflow_tash'}),
                    api.text({module: 'og.analytics.dropbox_tash'})
                ).then(function (tabs_tmpl, overflow_tmpl, dropbox_tmpl) {
                    if (!tabs_template) tabs_template = Handlebars.compile(tabs_tmpl);
                    if (!overflow_template) overflow_template = Handlebars.compile(overflow_tmpl);
                    if (!dropbox_template) dropbox_template = Handlebars.compile(dropbox_tmpl);
                    if (!$overflow_panel) $overflow_panel = $(overflow_template({pane: pane})).appendTo('body');
                    initialized = true;
                    loading = false;
                    // setup click handlers
                    $(selector + header + ' , .og-js-overflow-' + pane)
                        // handler for tabs (including the ones in the overflow pane)
                        .on('click', 'li[class^=og-tab-]', function (e) {
                            var id = extract_id($(this).attr('class')), index = extract_index(id);
                            if ($(e.target).hasClass('og-delete')) container.del(gadgets[index]);
                            else if (!$(this).hasClass('og-active')) {
                                update_tabs(id || null);
                                if (id) gadgets[index].gadget.resize();
                            }
                        });
                    if (!arr) update_tabs(null); else container.add(arr);
                    // implement drop
                    $(selector).droppable({
                        hoverClass: 'og-drop',
                        accept: function (draggable) {return $(draggable).is('li[class^=og-tab-]')}, // is it a tab...
                        over: function () {setTimeout(toggle_dropbox)}, // can't guarantee over and out fire in correct
                        out: function () {setTimeout(toggle_dropbox)},  // order, toggle function seems to solve issue
                        drop: function(e, ui) {
                            var has_ancestor = function (elm, sel) {return $(elm).closest('.' + sel).length},
                                pane_class = class_prefix + pane,
                                overflow_class = 'og-js-overflow-' + pane,
                                data = ui.draggable.data(),
                                gadget = data.gadget.config.options,
                                re = new RegExp(selector_prefix + '(.*?)\\s');
                            if (has_ancestor(ui.draggable, pane_class) || has_ancestor(ui.draggable, overflow_class)) {
                                ui.draggable.draggable('option', 'revert', true);
                            } else {
                                ui.draggable.draggable('option', 'revert', false);
                                gadget.selector = gadget.selector.replace(re, selector_prefix + pane + ' ');
                                container.add([data.gadget.config]);
                                setTimeout(data.handler); // setTimeout to ensure handler is called after drag evt ends
                            }
                        }
                    });
                    og.common.gadgets.manager.register(container);
                });
            };
            /**
             * @param {String|Array} data A String that defines what gadgets to load, or an Array of gadgets to load
             *
             * The data Array is a list of objects that describe the gadgets to load
             *     obj.gadget   Function
             *     obj.options  Object
             *     obj.name     String
             *     obj.margin   Boolean
             */
            container.add = function (data) {
                var panel_container = selector + ' .OG-gadget-container', new_gadgets, arr;
                if (!loading && !initialized)
                    return container.init(), setTimeout(container.add.partial(data || null), 10), container;
                if (!initialized) return setTimeout(container.add.partial(data || null), 10), container;
                if (!data) return container; // no gadgets for this container
                if (!selector) throw new TypeError('GadgetsContainer has not been initialized');
                var generate_arr = function (data) { // create gadgets object array from url args using default settings
                    var obj, type, gadgets = [], options = {};
                    obj = data.split(';').reduce(function (acc, val) {
                        var data = val.split(':');
                        acc[data[0]] = data[1].split(',');
                        return acc;
                    }, {});
                    // TODO: move default options to gadgets
                    options.timeseries = function (id) {
                        return {
                            gadget: 'og.common.gadgets.timeseries',
                            options: {id: id, datapoints_link: false, child: true},
                            name: 'Timeseries ' + id
                        }
                    };
                    options.surface = function (id) {
                        return {
                            gadget: 'og.common.gadgets.surface',
                            name: 'Surface ' + id,
                            options: {
                                child: true,
                                vol: [
                                    48.91469534705754, 45.8318374211111, 40.84562019478884, 38.603102543733854, 37.46690854016287, 35.005776740495115, 34.13202365676666, 33.76207767246494, 33.441983699602794, 33.20814034731022, 33.05551662552946, 32.905785060058065, 32.83676063826475, 32.99654198084455, 33.2475096657778, 33.347737101922746, 32.86896418821408, 31.932713989042146, 31.602015558675646, 31.833582702748224,
                                    44.217041253994296, 41.86518329033477, 37.90701197923069, 36.2725189417284, 35.1636403042937, 33.33766856303285, 32.662731602492826, 32.30180612251391, 32.153070995377355, 31.948365333951983, 31.80277298214066, 31.951002331221805, 32.17207912200023, 32.08364460243179, 31.729794788065284, 31.345797830181237, 30.909252869017457, 30.80651563383117, 30.91987376009676, 31.48931987987816,
                                    40.56210779567678, 38.540308756951255, 34.96739424446746, 33.99394291399909, 33.07896121195465, 31.713114451086042, 31.16933216940178, 30.944819108261214, 30.824510742460337, 30.740202231473017, 30.959943505064963, 30.815824277462646, 30.419113895685886, 30.125600588949148, 29.999494037133434, 29.96559996037962, 29.98976355041636, 30.1155294541958, 30.473168069075072, 31.148646131419937,
                                    36.52549992096878, 34.910625040226684, 32.54193134813007, 31.780987326158634, 31.087029309637405, 30.13411099420029, 29.719888389851267, 29.560178686437787, 29.582476764931187, 29.640662876932712, 29.296337715533404, 29.095656159297928, 29.059402587278516, 29.07816823825296, 29.110054534003954, 29.17206922576089, 29.34332461079515, 29.625338299255144, 30.052068000958016, 31.119670876356174,
                                    32.58126390238792, 31.51758184839067, 30.01229955587702, 29.611974145718012, 29.422080100690597, 28.506554775684755, 28.270320764459733, 28.29888926974069, 28.241573604749043, 28.071463201358647, 28.073538454212592, 28.119185592386636, 28.210048907071588, 28.323828996415866, 28.443583153489687, 28.56788958636875, 28.795952413371577, 29.15669045953298, 29.907443606949823, 31.310755536803413,
                                    28.96795814098852, 28.363269248078392, 27.539807857456616, 27.537926898694582, 27.400569893256506, 26.950592385698762, 26.921477491900653, 26.921688268390398, 26.939635881777114, 27.020674222916817, 27.16340242055404, 27.32562457745344, 27.483138561017594, 27.638465565223434, 27.813783272721775, 28.00921505059891, 28.43366377590284, 29.073300059658045, 29.947509627675274, 31.018793492421757,
                                    25.349216316583963, 25.23013334560887, 25.112198511979027, 25.50300570554272, 25.524666200465333, 25.502826898940967, 25.614902417047276, 25.71001545983247, 25.813575685394007, 26.03911360417169, 26.292538504045577, 26.590098663561747, 26.909638928386798, 27.224557989267606, 27.519630119361775, 27.789127466688868, 28.251438597455415, 28.780950413571144, 29.382835969936398, 30.15491231508969,
                                    21.983007812798135, 22.23055384771756, 22.69594043763327, 23.41784327090356, 23.65021079163232, 23.991546792576354, 24.21141941448089, 24.42668027518272, 24.677259757930194, 25.192529240634414, 25.651506404813436, 26.035629413225692, 26.35409398244672, 26.622106106732378, 26.85286703376402, 27.05722072397621, 27.408405233895365, 27.839154389338503, 28.4074344756339, 29.2580830212573,
                                    18.811192740781223, 19.39804306082863, 20.387185039535517, 21.455535006108338, 22.068631145737616, 22.417184416881412, 22.84790201418783, 23.25841046703947, 23.612728908067506, 24.172950633092196, 24.611351525322096, 24.974869177899368, 25.28766447211651, 25.563667187161947, 25.81162347016387, 26.037394450610112, 26.43778538555933, 26.946125363776012, 27.633123203560285, 28.65996146179972,
                                    15.318913801020829, 16.34314245774855, 17.945712377501696, 19.42012610495607, 20.24934435489939, 21.0254569003275, 21.61944772834452, 22.102331285602492, 22.508470202637536, 23.164541647753616, 23.686570893455837, 24.121820828253824, 24.496053232860138, 24.824917827167994, 25.118659756723744, 25.384364818392736, 25.850812375609483, 26.433378622351405, 27.203852348728162, 28.328056828093047,
                                    12.622155189358896, 13.934688975458629, 15.8787895869609, 17.45131252453414, 18.588087093475377, 19.593552692426268, 20.353853267871884, 20.976407719766122, 21.496312884152736, 22.31478641447131, 22.949453163511926, 23.469204639100933, 23.908534180950593, 24.28891665765194, 24.624497105559126, 24.924900997430242, 25.445694388864084, 26.08631641432582, 26.920449248831247, 28.116597301852902,
                                    13.128704354561686, 13.41733434460995, 14.276297016982948, 16.059937778230818, 17.145787346389856, 18.334635089422704, 19.210967399951436, 19.913233392003825, 20.50182335917778, 21.447150688586508, 22.183784388689897, 22.783324367784545, 23.28735081772387, 23.721150979657292, 24.101638562068448, 24.44039946978796, 25.02348612028896, 25.7334135261908, 26.649753813237183, 27.942298738317305,
                                    17.37383238056344, 16.12621282750413, 13.818643694638382, 15.084132438034386, 15.734920039034533, 17.285755263078258, 18.17304287438908, 18.883102896397265, 19.52360702910723, 20.540666747474575, 21.33546873088693, 21.998462035068464, 22.565608520947436, 23.05953419719903, 23.492088956970754, 23.875928052381763, 24.532971565386557, 25.32487181274386, 26.325736262284234, 27.708349183264367,
                                    21.958155349657584, 20.091990092341234, 15.461323128307114, 14.630981820175768, 15.61844643422288, 16.057735398629248, 17.278079037202883, 17.974144815982136, 18.442230833270692, 19.52431699513662, 20.48808100896847, 21.210622797959964, 21.80723664438358, 22.3236886602791, 22.786010770400114, 23.204312972695128, 23.933683532658492, 24.82332544873818, 25.936171472168358, 27.446691587027743,
                                    25.93748442573735, 23.75403065757964, 17.508435011083932, 14.323371459114831, 15.812442949284216, 15.02714798009071, 16.164351014575516, 17.242194272775105, 17.882495003168025, 18.576689334665613, 19.298997452024466, 20.202283745197562, 21.00100940292126, 21.603830961203276, 22.105978767580154, 22.543557154643263, 23.294818705868803, 24.234522283686623, 25.461256148900663, 27.124320272609943,
                                    29.647334484648262, 27.161943222179968, 19.330659264244954, 14.081105970891683, 15.965838476261467, 14.257734363992784, 15.248935122585266, 16.355251762796748, 17.297733580360052, 18.27054862316136, 18.751801343920167, 19.13844194141329, 19.78108426834407, 20.5382661899298, 21.248801421100715, 21.828266666185296, 22.68449940252864, 23.661818382217696, 24.92678899304387, 26.737239022099242,
                                    33.12761228987634, 30.351586829308232, 20.97296338082995, 13.893401644002001, 16.090196118535943, 13.666891508446652, 14.660902937896509, 15.914827681746601, 16.740033366419095, 18.017646861826478, 18.611235572339275, 18.95677833960281, 19.18260594504985, 19.503201770847273, 20.04424906957059, 20.683537524249683, 21.893549526007984, 23.091589397121286, 24.416765895708377, 26.28921850388062,
                                    36.40799328911491, 33.35041479178037, 22.466397438433226, 13.743837469531965, 16.19292512671293, 13.206220036243518, 14.05397878680784, 15.718296043266314, 16.83490678781717, 17.660491553156085, 18.547563126994277, 18.958315159097868, 19.158142019233495, 19.33899833189238, 19.485312705380277, 19.74585926866104, 20.72296472229649, 22.32053609117948, 23.92023863671903, 25.84029674421995,
                                    39.51166655111355, 36.18029500558661, 23.83414572403894, 13.621865618168682, 16.279129471774965, 12.839880439763792, 13.557260495984313, 15.26460269426967, 16.92423689766326, 17.963346294432046, 18.32122930158913, 18.981398535577814, 19.31877679230343, 19.389776096806692, 19.52771451403986, 19.617605881634724, 19.919680167829547, 21.199355550038547, 23.329172991534556, 25.421574901140527,
                                    42.45738031081145, 38.85913433449903, 25.094146490034564, 13.520447631949908, 16.35244060217202, 12.543160233896385, 13.151659641249115, 14.867596343136109, 16.627712492400654, 18.843342975875473, 18.76513063298974, 18.863971441873908, 19.356227076517516, 19.67335504409258, 19.673973024969282, 19.713974690152785, 19.846710760859818, 20.321422820973726, 22.40608988405136, 25.010207673279478
                                ],
                                xs: ['0.1', '0.25', '0.5', '0.75', '1', '1.5', '2', '2.5', '3', '4', '5', '6', '7', '8', '9', '10', '12', '15', '20', '30'],
                                zs: ['0.55', '0.6', '0.65', '0.7', '0.75', '0.8', '0.85', '0.9', '0.95', '1', '1.05', '1.1', '1.15', '1.2', '1.25', '1.3', '1.35', '1.4', '1.45', '1.5']
                            }
                        }
                    };
                    options.grid = function (id) {
                        return {gadget: 'og.analytics.Grid', name: 'grid ' + id, options: {}}
                    };
                    for (type in obj) if (obj.hasOwnProperty(type))
                        obj[type].forEach(function (val) {gadgets.push(options[type](val));});
                    return gadgets;
                };
                arr = typeof data === 'string' ? generate_arr(data) : data;
                new_gadgets = arr.map(function (obj) {
                    var id, gadget_class = 'OG-gadget-' + (id = counter++), gadget,
                        gadget_selector = panel_container + ' .' + gadget_class,
                        options = $.extend(true, obj.options || {}, {selector: gadget_selector}),
                        constructor = obj.gadget.split('.').reduce(function (acc, val) {return acc[val];}, window),
                        type = obj.gadget.replace(/^[a-z0-9.-_]+\.([a-z0-9.-_]+?)$/, '$1').toLowerCase();
                    $(panel_container)
                        .append('<div class="' + gadget_class + '" />')
                        .find('.' + gadget_class)
                        .css({height: '100%'});
                    gadgets.push(gadget = {id: id, config: obj, type: type, gadget: new constructor(options)});
                    return gadget;
                });
                update_tabs(new_gadgets[new_gadgets.length - 1].id);
                return container;
            };
            container.del = function (obj) {
                var id;
                $(selector + ' .OG-gadget-container .OG-gadget-' + obj.id).remove();
                gadgets.splice(gadgets.indexOf(obj), 1);
                id = gadgets.length
                    ? live_id === obj.id ? gadgets[gadgets.length - 1].id : live_id
                    : null;
                if (id) gadgets[extract_index(id)].gadget.resize();
                update_tabs(id); // new active tab or empty
            };
            container.alive = function () {return !!$(selector).length;};
            container.resize = function () {
                gadgets.forEach(function (obj) {
                    if (obj.id === live_id) {
                        $(selector + ' .ui-layout-content').show();
                        update_tabs();
                        obj.gadget.resize();
                    }
                });
            }
        };
    }
});