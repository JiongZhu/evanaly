/**
 * Created by wx6_2 on 2017/6/7.
 */
var contextPath = $('#ctxPath').text()

+function ($) {
    'use strict';

    // CAROUSEL CLASS DEFINITION
    // =========================

    var Slide = function (element, options) {
        this.$element = $(element)
        this.$indicators = this.$element.find('.w-slide-controller')
        this.options = options
        this.sliding =
            this.interval =
                this.$active =
                    this.$items = null

        this.options.keyboard && $(document).on('keydown.bs.slide', $.proxy(this.keydown, this))
        this.options.mousewheel && $(document).on('mousewheel.bs.slide', $.proxy(this.mousewheel, this))

    }

    Slide.TRANSITION_DURATION = 600

    Slide.DEFAULTS = {
        interval: 5000,
        pause: 'hover',
        wrap: true,
        keyboard: true,
        mousewheel: true
    }

    Slide.prototype.mousewheel = function (e) {
        var d = e.originalEvent.deltaY < 0 ? 1 : -1;
        switch (d) {
            case 1:
                this.prev();
                break
            case -1:
                this.next();
                break
            default:
                return
        }

        e.preventDefault()
    }

    Slide.prototype.keydown = function (e) {
        switch (e.which) {
            case 38:
                this.prev();
                break
            case 40:
                this.next();
                break
            default:
                return
        }

        e.preventDefault()
    }

    Slide.prototype.getItemIndex = function (item) {
        this.$items = item.parent().children('.w-slide-item')
        return this.$items.index(item || this.$active)
    }

    Slide.prototype.getItemForDirection = function (direction, active) {
        var delta = direction == 'prev' ? -1 : 1
        var activeIndex = this.getItemIndex(active)
        var itemIndex
        if ((delta == -1 && activeIndex == 0) || (delta == 1 && activeIndex == this.$items.length - 1))
            itemIndex = activeIndex;
        else
            itemIndex = (activeIndex + delta) % this.$items.length


        return this.$items.eq(itemIndex)
    }

    Slide.prototype.to = function (pos) {
        var that = this
        var activeIndex = this.getItemIndex(this.$active = this.$element.find('.w-slide-item.active'))

        if (pos > (this.$items.length - 1) || pos < 0) return

        if (this.sliding)       return this.$element.one('slid.bs.slide', function () {
            that.to(pos)
        }) // yes, "slid"

        return this.slide(pos > activeIndex ? 'next' : 'prev', this.$items.eq(pos))
    }

    Slide.prototype.next = function () {
        if (this.sliding) return
        return this.slide('next')
    }

    Slide.prototype.prev = function () {
        if (this.sliding) return
        return this.slide('prev')
    }

    Slide.prototype.slide = function (type, next) {
        var $active = this.$element.find('.w-slide-item.active')
        var $next = next || this.getItemForDirection(type, $active)
        var direction = type == 'next' ? 'up' : 'down'
        var fallback = type == 'next' ? 'first' : 'last'
        var that = this

        if (!$next.length) {
            if (!this.options.wrap) return
            $next = this.$element.find('.w-slide-item')[fallback]()
        }

        if ($next.hasClass('active')) return (this.sliding = false)

        var relatedTarget = $next[0]
        var slideEvent = $.Event('slide.bs.slide', {
            relatedTarget: relatedTarget,
            direction: direction
        })
        this.$element.trigger(slideEvent)
        if (slideEvent.isDefaultPrevented()) return

        this.sliding = true

        if (this.$indicators.length) {
            this.$indicators.find('.active').removeClass('active')
            var $nextIndicator = $(this.$indicators.children()[this.getItemIndex($next)])
            $nextIndicator && $nextIndicator.addClass('active')
        }

        var slidEvent = $.Event('slid.bs.slide', {relatedTarget: relatedTarget, direction: direction}) // yes, "slid"
        if ($.support.transition) {
            $next.addClass(type)
            $next[0].offsetHeight // force reflow
            $active.addClass(direction)
            $next.addClass(direction)
            $active
                .one('bsTransitionEnd', function () {
                    $next.removeClass([type, direction].join(' ')).addClass('active')
                    $active.removeClass(['active', direction].join(' '))
                    that.sliding = false
                    setTimeout(function () {
                        that.$element.trigger(slidEvent)
                    }, 0)
                })
                .emulateTransitionEnd(Slide.TRANSITION_DURATION)
        } else {
            $active.removeClass('active')
            $next.addClass('active')
            this.sliding = false
            this.$element.trigger(slidEvent)
        }

        return this
    }


    // CAROUSEL PLUGIN DEFINITION
    // ==========================

    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var data = $this.data('bs.slide')
            var options = $.extend({}, Slide.DEFAULTS, $this.data(), typeof option == 'object' && option)
            var action = typeof option == 'string' ? option : options.slide

            if (!data) $this.data('bs.slide', (data = new Slide(this, options)))
            if (typeof option == 'number') data.to(option)
            else if (action) data[action]()
        })
    }

    var old = $.fn.slide

    $.fn.slide = Plugin
    $.fn.slide.Constructor = Slide


    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.slide.noConflict = function () {
        $.fn.slide = old
        return this
    }


    // CAROUSEL DATA-API
    // =================

    var clickHandler = function (e) {
        var href
        var $this = $(this)
        var $target = $($this.attr('data-target') || (href = $this.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '')) // strip for ie7
        if (!$target.hasClass('w-slide')) return
        var options = $.extend({}, $target.data(), $this.data())
        var slideIndex = $this.attr('data-slide-to')
        if (slideIndex) options.interval = false

        Plugin.call($target, options)

        if (slideIndex) {
            $target.data('bs.slide').to(slideIndex)
        }

        e.preventDefault()
    }

    $(document)
        .on('click.bs.slide.data-api', '[data-slide]', clickHandler)
        .on('click.bs.slide.data-api', '[data-slide-to]', clickHandler)

    $(window).on('load', function () {
        $('[data-ride="slide"]').each(function () {
            var $slide = $(this)
            Plugin.call($slide, $slide.data())
        })
    })

}(jQuery);

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var User = function (element, options) {
        this.$element = $(element)
        this.$toolbox = $(this.$element.find('.nav-user')[0])
        this.$name = $(this.$element.find('.user-name')[0])

        this.$name.text(options.name)
        this.$name.hover($.proxy(this.position, this))
    }

    User.DEFAULTS = {
        name: '用户名'
    }

    User.prototype.position = function (e) {
        var left = (this.$name.outerWidth() - this.$toolbox.width()) / 2 + 2;
        this.$toolbox.css("left", left + "px");
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, User.DEFAULTS, typeof option == 'object' && option)

            new User($this, options);
        })
    }

    var old = $.fn.user

    $.fn.user = Plugin
    $.fn.user.Constructor = User


    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.user.noConflict = function () {
        $.fn.user = old
        return this
    }

}(jQuery);

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var LoginValidate = function (element, options) {
        this.$element = $(element)
        this.$noInput = $(this.$element.find('input[name="no"]')[0])
        this.$psInput = $(this.$element.find('input[name="password"]')[0])
        this.$validateInput = $(this.$element.find('input[name="validate"]')[0])
        this.$loginBtn = this.$element.find('#nologin-btn')
        this.$helpBlock = this.$element.find('#nologin-helpblock')
        this.$remember = $(this.$element.find('input[name="remember"]')[0])
        this.options = options

        this.$noInput.blur($.proxy(this.noValidate, this))
        this.$psInput.blur($.proxy(this.psValidate, this))
        this.$validateInput.blur($.proxy(this.codeValidate, this))
        this.$loginBtn.click($.proxy(this.login, this))
    }

    LoginValidate.DEFAULTS = {
        noReg: /^\d{11}$/,
        psReg: /^[\d\w]{6,12}$/,
        validateReg: /^[\d\w]{4}$/
    }

    LoginValidate.prototype.noValidate = function (e) {
        var regex = this.options.noReg
        var value = this.$noInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入工号!')
            this.$noInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$noInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$noInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('工号格式错误!')
            }
        }

        return false;
    }

    LoginValidate.prototype.psValidate = function (e) {
        var regex = this.options.psReg
        var value = this.$psInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入密码!')
            this.$psInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$psInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$psInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('密码格式错误!')
            }
        }

        return false;
    }

    LoginValidate.prototype.codeValidate = function (e) {
        var regex = this.options.validateReg
        var value = this.$validateInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入验证码!')
            this.$validateInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$validateInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$validateInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('验证码格式错误!')
            }
        }

        return false;
    }

    LoginValidate.prototype.login = function (e) {
        function success(data) {
            if (data == 'true') {
                window.location.href = contextPath+"/index.jsp";
            }
            else
                this.$helpBlock.css('display', 'block').text(data);
        }

        if (this.noValidate() && this.psValidate() && this.codeValidate()) {
            var no = this.$noInput.val()
            var password = this.$psInput.val()
            var validate = this.$validateInput.val()
            var remember = this.$remember.is(":checked")
            $.ajax({
                type: 'POST',
                url: contextPath + this.options.url,
                data: {
                    no: no,
                    password: password,
                    code: validate,
                    remember: remember
                },
                success: $.proxy(success, this)
            })
        }
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, LoginValidate.DEFAULTS, typeof option == 'object' && option)

            new LoginValidate($this, options);
        })
    }

    var old = $.fn.loginValidate

    $.fn.loginValidate = Plugin
    $.fn.loginValidate.Constructor = LoginValidate


    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.loginValidate.noConflict = function () {
        $.fn.loginValidate = old
        return this
    }

}(jQuery);

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var PhoneValidate = function (element, options) {
        this.$element = $(element)
        this.$phoneInput = $(this.$element.find('input[name="phone"]')[0])
        this.$codeInput = $(this.$element.find('input[name="code"]')[0])
        this.$sendCodeBtn = this.$element.find('#sendCode')
        this.$loginBtn = this.$element.find('#phonelogin-btn')
        this.$helpBlock = this.$element.find('#phonelogin-helpblock')
        this.options = options

        this.$phoneInput.blur($.proxy(this.phoneValidate, this))
        this.$codeInput.blur($.proxy(this.codeValidate, this))
        this.$sendCodeBtn.click($.proxy(this.sendCode, this))
        this.$loginBtn.click($.proxy(this.login, this))
    }

    PhoneValidate.DEFAULTS = {
        phoneReg: /^(13\d|14\d|15\d|18\d)\d{8}$/,
        codeReg: /^[\d\w]{4}$/
    }

    PhoneValidate.prototype.phoneValidate = function (e) {
        var regex = this.options.phoneReg
        var value = this.$phoneInput.val().trim()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入手机号!')
            this.$phoneInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$phoneInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$phoneInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('手机号格式错误!')
            }
        }

        return false;
    }

    PhoneValidate.prototype.sendCode = function (e) {
        var time = 60;
        var instance;

        function interval() {
            this.$sendCodeBtn.text(time-- + "秒后重新发送");
            if (time == -1) {
                this.$sendCodeBtn.removeAttr("disabled");
                this.$sendCodeBtn.text("发送验证码");
                clearInterval(instance);
            }
        }

        function success(data) {
            if (data == '') {
                this.$sendCodeBtn.attr("disabled", "true");
                instance = setInterval($.proxy(interval, this), 1000);
            }
            else
                this.$helpBlock.css('display', 'block').text(data);
        }

        if (this.phoneValidate()) {
            $.ajax({
                type: 'GET',
                url: contextPath + '/sendCode',
                data: {
                    phone: this.$phoneInput.val().trim()
                },
                success: $.proxy(success, this)
            })
        }
    }

    PhoneValidate.prototype.codeValidate = function (e) {
        var regex = this.options.codeReg
        var value = this.$codeInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入验证码!')
            this.$codeInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$codeInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$codeInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('验证码格式错误!')
            }
        }

        return false;
    }

    PhoneValidate.prototype.login = function (e) {
        function success(data) {
            if (data == 'true') {
                window.location.href = "/index.jsp";
                this.$helpBlock.css('display', 'none');
            }
            else
                this.$helpBlock.css('display', 'block').text(data);
        }

        if (this.phoneValidate() && this.codeValidate()) {
            $.ajax({
                type: 'GET',
                url: contextPath + '/phoneLogin',
                data: {
                    code: this.$codeInput.val().trim()
                },
                success: $.proxy(success, this)
            });
        }
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, PhoneValidate.DEFAULTS, typeof option == 'object' && option)

            new PhoneValidate($this, options);
        })
    }

    var old = $.fn.phoneValidate

    $.fn.phoneValidate = Plugin
    $.fn.phoneValidate.Constructor = PhoneValidate


    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.phoneValidate.noConflict = function () {
        $.fn.phoneValidate = old
        return this
    }

}(jQuery);

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var CitySelector = function (element, options) {
        this.$element = $(element)
        this.$cityList = this.$element.find('.city-list > ul')
        this.$selectedBox = $(this.$element.find('.city-selected')[0])
        this.$confirmBtn = this.$element.find("#city-confirm")
        this.$cityItem = null
        this.selectedCity = []
        this.selectedID = []
        this.cities = []
        this.options = options

        this.$selectedBox.empty()

        this.initCity()
        this.initSelectedBox()
        this.$selectedBox.off('click').on('click', 'span > .ico-close', $.proxy(this.removeSelected, this))
        this.$confirmBtn.off('click').on('click', $.proxy(this.confirm, this))
    }

    CitySelector.DEFAULTS = {
        multiple: false,
    }

    CitySelector.prototype.initCity = function (e) {
        var sort = [['A', 'B', 'C', 'D'], ['E', 'F', 'G', 'H'], ['I', 'J', 'K', 'L'], ['M', 'N', 'O', 'P'], ['Q', 'R', 'S', 'T'], ['U', 'V', 'W'], ['X', 'Y', 'Z']]

        function initTable(data) {
            var cities = $.parseJSON(data)
            this.cities = cities
            var mapped = {}

            for (var item in cities) {
                var c = pinyinUtil.getFirstLetter(cities[item]['name']).charAt(0)
                if (mapped[c] == null)
                    mapped[c] = [];
                mapped[c].push(cities[item])
            }

            var index = 0
            for (var item in sort) {
                for (var char in sort[item]) {
                    var html = ""
                    for (var city in mapped[sort[item][char]])
                        html += "<li data-id='" + mapped[sort[item][char]][city]['id'] + "'>" + mapped[sort[item][char]][city]['name'] + "</li>"

                    $(this.$cityList[index++]).empty().html(html)
                }
            }
            this.$cityItem = this.$cityList.find('li')
            this.$cityItem.on('click', $.proxy(this.select, this))
        }

        $.ajax({
            type: 'GET',
            async: false,
            url: contextPath + this.options.initCityURl,
            success: $.proxy(initTable, this)
        })
    }

    CitySelector.prototype.initSelectedBox = function (ev) {
        if (this.options.multiple) {
            $("#firstpanel-link").tab('show')
            this.selectedCity = this.options.relatedTarget.data("city") || []
            this.selectedID = this.options.relatedTarget.data("id") || []
            for (var city in this.selectedCity) {
                var nameAdd = '<span>' +
                    '<a data-id="' + this.selectedID[city] + '">' + this.selectedCity[city] + '</a>' +
                    '<i class="ico-close"></i>' +
                    '</span>';
                this.$selectedBox.append(nameAdd);
            }
        } else {
            this.selectedCity.push(this.options.relatedTarget.data("city"))
            this.selectedID.push(this.options.relatedTarget.data("id"))
            var map = {
                '#firstpanel-link': ['A', 'B', 'C', 'D'],
                "#secondpanel-link": ['E', 'F', 'G', 'H'],
                "#thirdpanel-link": ['I', 'J', 'K', 'L'],
                "#fourthpanel-link": ['M', 'N', 'O', 'P'],
                "#fifthpanel-link": ['Q', 'R', 'S', 'T'],
                "#sixthpanel-link": ['U', 'V', 'W'],
                "#seventhpanel-link": ['X', 'Y', 'Z'],

            }
            var c = pinyinUtil.getFirstLetter(this.selectedCity[0]).charAt(0)
            this.$cityList.find("li[data-id='" + this.selectedID[0] + "']").addClass('selected')

            for (var id in map) {
                if (map[id].indexOf(c) != -1) {
                    $(id).tab('show')
                    break;
                }
            }
        }
    }

    CitySelector.prototype.select = function (e) {
        if (this.options.multiple) {
            var name = $(e.target).text();
            var nameAdd = '<span>' +
                '<a data-id="' + $(e.target).data('id') + '">' + name + '</a>' +
                '<i class="ico-close"></i>' +
                '</span>';

            var index = this.selectedCity.indexOf(name);
            if (index == -1) {
                this.$selectedBox.append(nameAdd);
                this.selectedCity.push(name)
                this.selectedID.push($(e.target).data('id'))
            }
        } else {
            this.$cityItem.removeClass('selected')
            $(e.target).addClass('selected')
            this.selectedCity=[];
            this.selectedID=[];
            this.selectedCity.push($(e.target).text())
            this.selectedID.push($(e.target).data('id'))
        }
    }

    CitySelector.prototype.removeSelected = function (e) {
        this.selectedCity.splice(this.selectedCity.indexOf($(e.target).siblings('a').text()), 1)
        this.selectedID.splice(this.selectedID.indexOf($(e.target).siblings('a').data('id')), 1)
        $(e.target).parent().remove();
    }

    CitySelector.prototype.confirm = function (e) {
        this.options.target.val(this.selectedCity)
        this.options.target.data("id", this.selectedID)
        this.options.relatedTarget.data("id", this.selectedID)
        this.options.relatedTarget.data("city", this.selectedCity)
        this.$element.modal('hide')
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, CitySelector.DEFAULTS, typeof option == 'object' && option)
            new CitySelector(this, options)
        })
    }

    var old = $.fn.citySelector

    $.fn.citySelector = Plugin
    $.fn.citySelector.Constructor = CitySelector

    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.citySelector.noConflict = function () {
        $.fn.citySelector = old
        return this
    }

}(jQuery);