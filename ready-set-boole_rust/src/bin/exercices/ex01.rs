use super::ex00::adder;

pub fn multiplier(a: u32, b: u32) -> u32 {
    let mut a = a;
    let mut b = b;
    let mut result: u32 = 0;

    if a == 0 {
        return 0;
    }
    while b != 0 {
        if b & 1 != 0 {
            result = adder(a, result);
        }
        a = a << 1;
        b = b >> 1;
    }
    return result;
}
