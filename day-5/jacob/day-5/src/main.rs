use std::cmp;
use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn main() {
    if let Ok(lines) = read_lines("./input.txt") {

        let mut i = 0;
        let seeds: &mut Vec<(i64, i64)> = &mut Vec::new();
        let mut new_map_line = false;
        let mut maps :Vec<Vec<(i64, i64, i64)>> = Vec::new();
        for line in lines {
            if let Ok(line_str) = line {
                if i == 0 {
                    let seed_list = line_str.split(":").collect::<Vec<&str>>()[1];
                    let seed_strings = seed_list.split(" ").collect::<Vec<&str>>();
                    let mut counter = 0;
                    let mut start = 0;
                    for seed_string in seed_strings {
                        if seed_string.len() > 0 {
                            if counter % 2 == 0 {
                                start = seed_string.parse::<i64>().unwrap();
                            } else {
                                seeds.push((start, start + seed_string.parse::<i64>().unwrap()));
                            }
                            counter += 1;
                        }
                    }
                } else {
                    if line_str.len() == 0 {
                        // Create new map next line
                        new_map_line = true;
                        continue;
                    } else {
                        if new_map_line {
                            // Initialize new map
                            new_map_line = false;
                            maps.push(Vec::new());
                        } else {
                            // Add current entry to current map
                            let last = maps.len() - 1;
                            let entries = line_str.split(" ").collect::<Vec<&str>>();
                            let mut int_entries = Vec::new();
                            for entry in entries {
                                int_entries.push(entry.parse::<i64>().unwrap());
                            }
                            maps[last].push((int_entries[0],int_entries[1],int_entries[2]));
                        }
                    }
                }
            }
            i += 1;
        }
        
        let new: &mut Vec<(i64, i64)> = &mut Vec::new();
        for i in 0..maps.len() {
            new.clear();

            'seed_loop: while let Some((start, end)) = seeds.pop() { 
                for (dest, src, rng) in &maps[i] {
                    let overlap_start = cmp::max(start, *src);
                    let overlap_end: i64 = cmp::min(end, *src + *rng);
                    if overlap_start < overlap_end {
                        new.push((overlap_start - *src + *dest, overlap_end - *src + *dest));
                        if overlap_start > start {
                            seeds.push((start, overlap_start));
                        }
                        if end > overlap_end {
                            seeds.push((overlap_end, end));
                        }
                        continue 'seed_loop;
                    }
                }
                new.push((start, end));
            }
            for new_seed in &mut *new {
                seeds.push((*new_seed).clone());
            }
        }

        let mut min = i64::MAX;
        for seed_range in seeds {
            min = cmp::min(min, seed_range.0);
        }
        println!("{}", min);
    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}
