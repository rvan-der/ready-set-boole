use std::env;

pub mod exercices;

use crate::exercices::ex02::gray_code;

fn main() {
    let args: Vec<String> = env::args().collect();
    if args.len() != 2 {
        println!("One u32 needed.");
        return;
    }
    let n = args[1].parse::<u32>().unwrap();
    println!("gray_code({}) = {}", n, gray_code(n));
}