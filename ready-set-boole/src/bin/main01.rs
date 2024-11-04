use std::env;

pub mod exercices;
use crate::exercices::ex01::multiplier;

fn main() {
    let args: Vec<String> = env::args().collect();
    if args.len() != 3 {
        println!("Two u32 needed.");
        return;
    }
    let a = args[1].parse::<u32>().unwrap();
    let b = args[2].parse::<u32>().unwrap();
    println!("{} * {} = {}", a, b, multiplier(a, b));
}
