function MDCalc(z) {
    a = z.bili;
    b = z.inr;
    c = z.cr;
    hd = z.hd;
    na = z.na;
    if (pref == "si") {
        cr_conv = 1 / 88.4;
        bili_conv = 1 / 17.1
    } else if (pref == "us") {
        bili_conv = 1;
        cr_conv = 1;
    }
    a = a * bili_conv;
    c = c * cr_conv;
    if (na < 125) {
        na = 125;
    }
    if (na > 137) {
        na = 137;
    }
    if (hd == 1 || c > 4) {
        c = 4;
    }
    if (a < 1) {
        a = 1
    }
    if (b < 1) {
        b = 1
    }
    if (c < 1) {
        c = 1
    }
    value = 10 * (.378 * Math.log(a) + 1.12 * Math.log(b) + .957 * Math.log(c) + .643);
    if (value < 6) {
        value = 6
    }
    var res = Round(value, 0);
    if (res > 11) {
        res = res + 1.32 * (137 - na) - (0.033 * res * (137 - na));
    }
    res = Round(res, 0);
    Answer(res);
    var rate = '';
    if (res <= 9) {
        rate = '1.9%';
    } else if (res <= 19) {
        rate = '6.0%';
    } else if (res <= 29) {
        rate = '19.6%';
    } else if (res <= 39) {
        rate = '52.6%';
    } else {
        rate = '71.3%';
    }
    console.log(res);
    Text(rate);
}